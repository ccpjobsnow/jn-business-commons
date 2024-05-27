package com.jn.commons.entities.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.JnEntityDisposableRecords;

public abstract class JnDisposableEntity extends JnBaseEntity {

	public final JnDiposableRecordTimeExpiration timeOption;
	
	protected JnDisposableEntity(JnDiposableRecordTimeExpiration timeOption, CcpEntityField[] fields) {
		super(fields);
		this.timeOption = timeOption;
	}

	public final String getId(CcpJsonRepresentation values) {
		Long time = System.currentTimeMillis();
		String formattedCurrentDate = this.timeOption.getFormattedCurrentDate(time);

		ArrayList<Object> onlyPrimaryKeysValues = new ArrayList<>();
		onlyPrimaryKeysValues.add(formattedCurrentDate);
		ArrayList<Object> sortedPrimaryKeyValues = this.getSortedPrimaryKeyValues(values);
		onlyPrimaryKeysValues.addAll(sortedPrimaryKeyValues);
		
		String replace = onlyPrimaryKeysValues.toString().replace("[", "").replace("]", "");
		String hash = new CcpStringDecorator(replace).hash().asString("SHA1");
		return hash;
	}

	private final String getCopyId(CcpJsonRepresentation values) {

		ArrayList<Object> onlyPrimaryKeysValues = new ArrayList<>();
		ArrayList<Object> sortedPrimaryKeyValues = this.getSortedPrimaryKeyValues(values);
		
		boolean hasNoPrimaryKey = sortedPrimaryKeyValues.isEmpty();
		if(hasNoPrimaryKey) {
			String entityName = this.getEntityName();
			throw new RuntimeException("This entity must have at least one field mapped as primary key mapped. Entity: " + entityName);
		}
		onlyPrimaryKeysValues.addAll(sortedPrimaryKeyValues);
		
		String replace = onlyPrimaryKeysValues.toString().replace("[", "").replace("]", "");
		String hash = new CcpStringDecorator(replace).hash().asString("SHA1");
		return hash;
	}

	
	public CcpJsonRepresentation getCopyIdToSearch(CcpJsonRepresentation json) {
		
		String id = this.getCopyId(json);
		
		String entityName = this.getEntityName();
		
		CcpJsonRepresentation copyIdToSearch = CcpConstants.EMPTY_JSON.put("id", id).put("entity", entityName);
		return copyIdToSearch;
	}
	
	public final CcpBulkItem getRecordToBulkOperation(CcpJsonRepresentation values, CcpEntityOperationType operation) {
		CcpJsonRepresentation recordCopy = this.getRecordCopy(values);
		
		CcpBulkItem ccpBulkItem = new CcpBulkItem(recordCopy, operation, JnEntityDisposableRecords.INSTANCE);
		
		return ccpBulkItem;
	}
	
	private boolean saveCopy(CcpJsonRepresentation values) {
		
		CcpJsonRepresentation recordCopyToSave = this.getRecordCopy(values);
		
		boolean createdCopy = JnEntityDisposableRecords.INSTANCE.create(recordCopyToSave);
		
		return createdCopy;
	}

	private CcpJsonRepresentation getRecordCopy(CcpJsonRepresentation values) {
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(values);

		Long nextTimeStamp = this.timeOption.getNextTimeStamp();
		CcpJsonRepresentation onlyExistingFields = this.getOnlyExistingFields(values);
		CcpJsonRepresentation recordCopyToSave = copyIdToSearch
				.put("timestamp", nextTimeStamp)
				.put("json", onlyExistingFields)
				;
		return recordCopyToSave;
	}

	public final boolean create(CcpJsonRepresentation values) {
		
		boolean created = super.create(values);
		
		boolean updated = created == false;
		this.saveCopy(values);
		return updated;
	}

	
	public final CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation data, String id) {
		CcpJsonRepresentation createOrUpdate = super.createOrUpdate(data, id);
		boolean saveCopy = this.saveCopy(createOrUpdate);
		CcpJsonRepresentation put = createOrUpdate.put("createCopy", saveCopy);
		return put;
	}
	
	public final CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation values) {
		CcpJsonRepresentation createOrUpdate = super.createOrUpdate(values);
		boolean saveCopy = this.saveCopy(createOrUpdate);
		CcpJsonRepresentation put = createOrUpdate.put("createCopy", saveCopy);
		return put;
	}
	
	public final boolean delete(CcpJsonRepresentation values) {
		boolean delete = super.delete(values);
		
		if(delete == false) {
			return false;
		}
		this.deleteCopy(values);
		return delete;
	}

	private boolean deleteCopy(CcpJsonRepresentation values) {
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(values);
		boolean deleteCopy = JnEntityDisposableRecords.INSTANCE.delete(copyIdToSearch);
		return deleteCopy;
	}
	
	public final boolean delete(String id) {
		
		boolean delete = super.delete(id);
		
		if(delete == false) {
			return false;
		}
		CcpJsonRepresentation values = this.getOneById(id);
		boolean deleteCopy = this.deleteCopy(values);
		return deleteCopy;
	}
	
	public final boolean exists(CcpJsonRepresentation data) {
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(data);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation putAll = copyIdToSearch.putAll(data);
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, this, JnEntityDisposableRecords.INSTANCE);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, data);
		
		if(isPresentInOriginalEntity) {
			return true;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecords.INSTANCE.isPresentInThisUnionAll(unionAll, copyIdToSearch) == false;
		
		if(isNotPresentInCopyEntity) {
			return false;
		}
		
		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(JnEntityDisposableRecords.INSTANCE, copyIdToSearch);
		Long timeStamp = requiredEntityRow.getAsLongNumber("timestamp");
		
		boolean obsoleteTimeStamp = timeStamp <= System.currentTimeMillis();
		
		if(obsoleteTimeStamp) {
			return false;
		}
		
		return true;
	}
	
	
	public final CcpJsonRepresentation getOneById(CcpJsonRepresentation json) {
		
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		CcpJsonRepresentation putAll = copyIdToSearch.putAll(json);
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, this, JnEntityDisposableRecords.INSTANCE);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, putAll);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(this, putAll);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecords.INSTANCE.isPresentInThisUnionAll(unionAll, json) == false;

		if(isNotPresentInCopyEntity) {
			CcpJsonRepresentation oneById = super.getOneById(putAll);
			return oneById;
		}

		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(JnEntityDisposableRecords.INSTANCE, putAll);
		Long timeStamp = requiredEntityRow.getAsLongNumber("timestamp");
		
		boolean validTimeStamp = timeStamp > System.currentTimeMillis();
		
		if(validTimeStamp) {
			CcpJsonRepresentation innerJson = requiredEntityRow.getInnerJson("json");
			return innerJson;
		}
		
		CcpJsonRepresentation oneById = super.getOneById(putAll);
		
		return oneById;
	}

	public final CcpJsonRepresentation getOneById(CcpJsonRepresentation json, Function<CcpJsonRepresentation, CcpJsonRepresentation> ifNotFound) {
		
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation putAll = copyIdToSearch.putAll(json);
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, this, JnEntityDisposableRecords.INSTANCE);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, json);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(this, json);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecords.INSTANCE.isPresentInThisUnionAll(unionAll, copyIdToSearch) == false;

		if(isNotPresentInCopyEntity) {
			CcpJsonRepresentation oneById = super.getOneById(json, ifNotFound);
			return oneById;
		}

		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(JnEntityDisposableRecords.INSTANCE, copyIdToSearch);
		Long timeStamp = requiredEntityRow.getAsLongNumber("timestamp");
		
		boolean validTimeStamp = timeStamp > System.currentTimeMillis();
		
		if(validTimeStamp) {
			return requiredEntityRow;
		}
		
		CcpJsonRepresentation oneById = super.getOneById(json, ifNotFound);
		return oneById;
	}
	
	public final boolean canSaveCopy() {
		return true;
	}

	public List<CcpJsonRepresentation> getParametersToSearch(CcpJsonRepresentation json) {
		List<CcpJsonRepresentation> mainParametersToSearch = super.getParametersToSearch(json);
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		List<CcpJsonRepresentation> othersParametersToSearch = JnEntityDisposableRecords.INSTANCE.getParametersToSearch(copyIdToSearch);
		ArrayList<CcpJsonRepresentation> result = new ArrayList<>();
		result.addAll(othersParametersToSearch);
		result.addAll(mainParametersToSearch);
		return result;
	}

	public boolean isPresentInThisUnionAll(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {
		
		boolean presentInThisUnionAll = super.isPresentInThisUnionAll(unionAll, json);
		
		if(presentInThisUnionAll) {
			return true;
		}
		
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);

		boolean notFoundInDisposable = JnEntityDisposableRecords.INSTANCE.isPresentInThisUnionAll(unionAll, copyIdToSearch) == false;
		
		if(notFoundInDisposable) {
			return false;
		}
		
		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(JnEntityDisposableRecords.INSTANCE, copyIdToSearch);
		
		boolean valid = this.isValidTimestamp(requiredEntityRow);
		return valid;
	}

	private boolean isValidTimestamp(CcpJsonRepresentation requiredEntityRow) {
		
		String timeStampFieldName = JnEntityDisposableRecords.Fields.timestamp.name();
		
		boolean recordNotFound = requiredEntityRow.containsAllKeys(timeStampFieldName) == false;
		if(recordNotFound) {
			return false;
		}
		
		Long timeStamp = requiredEntityRow.getAsLongNumber(timeStampFieldName);
		
		if(timeStamp > System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	public CcpJsonRepresentation getRecordFromUnionAll(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {
		
		CcpJsonRepresentation recordFromUnionAll = super.getRecordFromUnionAll(unionAll, json);
		
		boolean recordFound = recordFromUnionAll.isEmpty() == false;
		
		if(recordFound) {
			return recordFromUnionAll;
		}
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		CcpJsonRepresentation recordFromDisposable = JnEntityDisposableRecords.INSTANCE.getRecordFromUnionAll(unionAll, copyIdToSearch);
		
		boolean isInvalid = this.isValidTimestamp(recordFromDisposable) == false;
	
		if(isInvalid) {
			return CcpConstants.EMPTY_JSON;
		}
		
		CcpJsonRepresentation innerJson = recordFromDisposable.getInnerJson(JnEntityDisposableRecords.Fields.json.name());
		return innerJson;
	}
}

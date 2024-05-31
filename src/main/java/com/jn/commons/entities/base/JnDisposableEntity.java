package com.jn.commons.entities.base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpHashDecorator;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.JnEntityDisposableRecord;

public abstract class JnDisposableEntity extends JnBaseEntity {

	public final JnDiposableRecordTimeExpiration timeOption;
	
	protected JnDisposableEntity(JnDiposableRecordTimeExpiration timeOption, CcpEntityField[] fields) {
		super(fields);
		this.timeOption = timeOption;
	}

	public final String calculateId(CcpJsonRepresentation json) {
		Long time = System.currentTimeMillis();
		String formattedCurrentDate = this.timeOption.getFormattedCurrentDate(time);

		ArrayList<Object> onlyPrimaryKeysValues = new ArrayList<>();
		onlyPrimaryKeysValues.add(formattedCurrentDate);
		ArrayList<Object> sortedPrimaryKeyValues = this.getSortedPrimaryKeyValues(json);
		onlyPrimaryKeysValues.addAll(sortedPrimaryKeyValues);
		
		String replace = onlyPrimaryKeysValues.toString().replace("[", "").replace("]", "");
		CcpHashDecorator hash2 = new CcpStringDecorator(replace).hash();
		String hash = hash2.asString("SHA1");
		return hash;
	}

	private final String getCopyId(CcpJsonRepresentation json) {

		ArrayList<Object> onlyPrimaryKeysValues = new ArrayList<>();
		ArrayList<Object> sortedPrimaryKeyValues = this.getSortedPrimaryKeyValues(json);
		
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
	
	public final CcpBulkItem getRecordCopyToBulkOperation(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		CcpJsonRepresentation recordCopy = this.getRecordCopy(json);
		
		CcpBulkItem ccpBulkItem = new CcpBulkItem(recordCopy, operation, JnEntityDisposableRecord.INSTANCE);
		
		return ccpBulkItem;
	}
	
	private boolean saveCopy(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation recordCopyToSave = this.getRecordCopy(json);
		
		boolean createdCopy = JnEntityDisposableRecord.INSTANCE.create(recordCopyToSave);
		
		return createdCopy;
	}

	private CcpJsonRepresentation getRecordCopy(CcpJsonRepresentation json) {
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);

		Long nextTimeStamp = this.timeOption.getNextTimeStamp();
		String nextDate = this.timeOption.getNextDate();
		CcpJsonRepresentation onlyExistingFields = this.getOnlyExistingFields(json);
		CcpJsonRepresentation recordCopyToSave = copyIdToSearch
				.put("timestamp", nextTimeStamp)
				.put("json", onlyExistingFields)
				.put("date", nextDate)
				;
		return recordCopyToSave;
	}

	public final boolean create(CcpJsonRepresentation json) {
		
		boolean created = super.create(json);
		
		this.saveCopy(json);
		return created;
	}

	public final CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json, String id) {
		
		CcpJsonRepresentation createOrUpdate = super.createOrUpdate(json, id);
		
		boolean saveCopy = this.saveCopy(createOrUpdate);
		
		CcpJsonRepresentation put = createOrUpdate.put("createCopy", saveCopy);
		
		return put;
	}
	
	public final CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation createOrUpdate = super.createOrUpdate(json);
		
		boolean saveCopy = this.saveCopy(createOrUpdate);

		CcpJsonRepresentation put = createOrUpdate.put("createCopy", saveCopy);
		
		return put;
	}
	
	public final boolean delete(CcpJsonRepresentation json) {
		
		boolean delete = super.delete(json);
		
		this.deleteCopy(json);

		return delete;
	}

	private boolean deleteCopy(CcpJsonRepresentation json) {
	
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		
		boolean deleteCopy = JnEntityDisposableRecord.INSTANCE.delete(copyIdToSearch);
		
		return deleteCopy;
	}
	
	public final boolean delete(String id) {
		
		boolean delete = super.delete(id);
		
		if(delete == false) {
			return false;
		}
		CcpJsonRepresentation json = this.getOneById(id);
		boolean deleteCopy = this.deleteCopy(json);
		return deleteCopy;
	}
	
	public final boolean exists(CcpJsonRepresentation json) {
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation putAll = copyIdToSearch.putAll(json);
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, this, JnEntityDisposableRecord.INSTANCE);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, json);
		
		if(isPresentInOriginalEntity) {
			return true;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.INSTANCE.isPresentInThisUnionAll(unionAll, copyIdToSearch) == false;
		
		if(isNotPresentInCopyEntity) {
			return false;
		}
		
		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(JnEntityDisposableRecord.INSTANCE, copyIdToSearch);
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
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, this, JnEntityDisposableRecord.INSTANCE);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, putAll);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(this, putAll);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.INSTANCE.isPresentInThisUnionAll(unionAll, json) == false;

		if(isNotPresentInCopyEntity) {
			CcpJsonRepresentation oneById = super.getOneById(putAll);
			return oneById;
		}

		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(JnEntityDisposableRecord.INSTANCE, putAll);
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
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, this, JnEntityDisposableRecord.INSTANCE);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, json);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(this, json);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.INSTANCE.isPresentInThisUnionAll(unionAll, copyIdToSearch) == false;

		if(isNotPresentInCopyEntity) {
			CcpJsonRepresentation oneById = super.getOneById(json, ifNotFound);
			return oneById;
		}

		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(JnEntityDisposableRecord.INSTANCE, copyIdToSearch);
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
		List<CcpJsonRepresentation> othersParametersToSearch = JnEntityDisposableRecord.INSTANCE.getParametersToSearch(copyIdToSearch);
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

		boolean notFoundInDisposable = JnEntityDisposableRecord.INSTANCE.isPresentInThisUnionAll(unionAll, copyIdToSearch) == false;
		
		if(notFoundInDisposable) {
			return false;
		}
		
		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(JnEntityDisposableRecord.INSTANCE, copyIdToSearch);
		
		boolean valid = this.isValidTimestamp(requiredEntityRow);
		return valid;
	}

	private boolean isValidTimestamp(CcpJsonRepresentation requiredEntityRow) {
		
		String timeStampFieldName = JnEntityDisposableRecord.Fields.timestamp.name();
		
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
		CcpJsonRepresentation recordFromDisposable = JnEntityDisposableRecord.INSTANCE.getRecordFromUnionAll(unionAll, copyIdToSearch);
		
		boolean isInvalid = this.isValidTimestamp(recordFromDisposable) == false;
	
		if(isInvalid) {
			return CcpConstants.EMPTY_JSON;
		}
		
		CcpJsonRepresentation innerJson = recordFromDisposable.getInnerJson(JnEntityDisposableRecord.Fields.json.name());
		return innerJson;
	}
}

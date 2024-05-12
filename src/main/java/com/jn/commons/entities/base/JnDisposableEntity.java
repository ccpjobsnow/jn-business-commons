package com.jn.commons.entities.base;

import java.util.ArrayList;
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

	private final JnDiposableRecordTimeExpiration timeOption;
	
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

	
	public CcpJsonRepresentation getCopyIdToSearch(CcpJsonRepresentation data) {
		
		String id = this.getId(data);
		
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
		boolean deleteCopy = this.deleteCopy(values);
		return deleteCopy;
	}

	private boolean deleteCopy(CcpJsonRepresentation values) {
		String id = this.getId(values);
		boolean doesNotExist = this.exists(id) == false;
	
		if(doesNotExist) {
			return false;
		}
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

		boolean isPresentInOriginalEntity = unionAll.isPresent(this, data);
		
		if(isPresentInOriginalEntity) {
			return true;
		}
	
		boolean isNotPresentInCopyEntity = unionAll.isPresent(JnEntityDisposableRecords.INSTANCE, copyIdToSearch) == false;
		
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
	
	
	public final CcpJsonRepresentation getOneById(CcpJsonRepresentation data) {
		
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(data);
		CcpJsonRepresentation putAll = copyIdToSearch.putAll(data);
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, this, JnEntityDisposableRecords.INSTANCE);

		boolean isPresentInOriginalEntity = unionAll.isPresent(this, putAll);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(this, putAll);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = unionAll.isPresent(JnEntityDisposableRecords.INSTANCE, putAll) == false;

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

	public final CcpJsonRepresentation getOneById(CcpJsonRepresentation data, Function<CcpJsonRepresentation, CcpJsonRepresentation> ifNotFound) {
		
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(data);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation putAll = copyIdToSearch.putAll(data);
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, this, JnEntityDisposableRecords.INSTANCE);

		boolean isPresentInOriginalEntity = unionAll.isPresent(this, data);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(this, data);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = unionAll.isPresent(JnEntityDisposableRecords.INSTANCE, copyIdToSearch) == false;

		if(isNotPresentInCopyEntity) {
			CcpJsonRepresentation oneById = super.getOneById(data, ifNotFound);
			return oneById;
		}

		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(JnEntityDisposableRecords.INSTANCE, copyIdToSearch);
		Long timeStamp = requiredEntityRow.getAsLongNumber("timestamp");
		
		boolean validTimeStamp = timeStamp > System.currentTimeMillis();
		
		if(validTimeStamp) {
			return requiredEntityRow;
		}
		
		CcpJsonRepresentation oneById = super.getOneById(data, ifNotFound);
		return oneById;
	}
	
	public final boolean canSaveCopy() {
		return true;
	}

}

package com.jn.commons.entities.base;

import java.util.ArrayList;
import java.util.Arrays;
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

	private final JnRecordStorageTimeExpiration timeOption;
	
	protected JnDisposableEntity(JnRecordStorageTimeExpiration timeOption, CcpEntityField[] fields) {
		super(fields);
		this.timeOption = timeOption;
	}

	public String getId(CcpJsonRepresentation values) {
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

	private String getCopyId(CcpJsonRepresentation values) {

		String entityName = this.getEntityName();

		ArrayList<Object> onlyPrimaryKeysValues = new ArrayList<>();
		onlyPrimaryKeysValues.add(entityName);
		ArrayList<Object> sortedPrimaryKeyValues = this.getSortedPrimaryKeyValues(values);
		onlyPrimaryKeysValues.addAll(sortedPrimaryKeyValues);
		
		String replace = onlyPrimaryKeysValues.toString().replace("[", "").replace("]", "");
		String hash = new CcpStringDecorator(replace).hash().asString("SHA1");
		return hash;
	}
	
	private CcpJsonRepresentation getCopyIdToSearch(CcpJsonRepresentation data) {
		String copyId = this.getCopyId(data);
		
		String entityName = this.getEntityName();
		
		CcpJsonRepresentation copyIdToSearch = CcpConstants.EMPTY_JSON.put("id", copyId).put("entity", entityName);
		return copyIdToSearch;
	}
	
	public CcpBulkItem getRecordToBulkOperation(CcpJsonRepresentation values, CcpEntityOperationType operation) {
		CcpJsonRepresentation recordCopy = this.getRecordCopy(values);
		
		CcpBulkItem ccpBulkItem = new CcpBulkItem(recordCopy, operation, JnEntityDisposableRecords.INSTANCE);
		
		return ccpBulkItem;
	}
	
	private boolean createCopy(CcpJsonRepresentation values) {
		
		CcpJsonRepresentation recordCopyToSave = this.getRecordCopy(values);
		
		boolean createdCopy = JnEntityDisposableRecords.INSTANCE.create(recordCopyToSave);
		
		return createdCopy;
	}

	private CcpJsonRepresentation getRecordCopy(CcpJsonRepresentation values) {
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(values);

		String copyId = this.getCopyId(values);

		Long nextTimeStamp = this.timeOption.getNextTimeStamp();
		CcpJsonRepresentation onlyExistingFields = this.getOnlyExistingFields(values);
		CcpJsonRepresentation recordCopyToSave = copyIdToSearch
				.put("timestamp", nextTimeStamp)
				.put("json", onlyExistingFields)
				.put("id", copyId)
				;
		return recordCopyToSave;
	}

	public boolean create(CcpJsonRepresentation values) {
		
		boolean created = super.create(values);
		
		boolean updated = created == false;
		
		if(updated) {
			return false;
		}
		
		boolean createCopy = this.createCopy(values);
		return createCopy;
	}

	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation data, String id) {
		CcpJsonRepresentation createOrUpdate = super.createOrUpdate(data, id);
		boolean createCopy = this.createCopy(createOrUpdate);
		CcpJsonRepresentation put = createOrUpdate.put("createCopy", createCopy);
		return put;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation values) {
		CcpJsonRepresentation createOrUpdate = super.createOrUpdate(values);
		boolean createCopy = this.createCopy(createOrUpdate);
		CcpJsonRepresentation put = createOrUpdate.put("createCopy", createCopy);
		return put;
	}
	
	public boolean delete(CcpJsonRepresentation values) {
		boolean delete = super.delete(values);
		
		if(delete == false) {
			return false;
		}
		boolean deleteCopy = this.deleteCopy(values);
		return deleteCopy;
	}

	private boolean deleteCopy(CcpJsonRepresentation values) {
		String copyId = this.getCopyId(values);
		boolean doesNotExist = this.exists(copyId) == false;
	
		if(doesNotExist) {
			return false;
		}
		
		boolean deleteCopy = JnEntityDisposableRecords.INSTANCE.delete(copyId);
		return deleteCopy;
	}
	
	public boolean delete(String id) {
		
		boolean delete = super.delete(id);
		
		if(delete == false) {
			return false;
		}
		CcpJsonRepresentation values = this.getOneById(id);
		boolean deleteCopy = this.deleteCopy(values);
		return deleteCopy;
	}
	
	public boolean exists(CcpJsonRepresentation data) {
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(data);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpSelectUnionAll unionAll = crud.unionAll(Arrays.asList(copyIdToSearch, data), this, JnEntityDisposableRecords.INSTANCE);

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
	
	
	public CcpJsonRepresentation getOneById(CcpJsonRepresentation data) {
		
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(data);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpSelectUnionAll unionAll = crud.unionAll(Arrays.asList(copyIdToSearch, data), this, JnEntityDisposableRecords.INSTANCE);

		boolean isPresentInOriginalEntity = unionAll.isPresent(this, data);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(this, data);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = unionAll.isPresent(JnEntityDisposableRecords.INSTANCE, copyIdToSearch) == false;

		if(isNotPresentInCopyEntity) {
			CcpJsonRepresentation oneById = super.getOneById(data);
			return oneById;
		}

		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(JnEntityDisposableRecords.INSTANCE, copyIdToSearch);
		Long timeStamp = requiredEntityRow.getAsLongNumber("timestamp");
		
		boolean validTimeStamp = timeStamp > System.currentTimeMillis();
		
		if(validTimeStamp) {
			return requiredEntityRow;
		}
		
		CcpJsonRepresentation oneById = super.getOneById(data);
		
		return oneById;
	}

	public CcpJsonRepresentation getOneById(CcpJsonRepresentation data, Function<CcpJsonRepresentation, CcpJsonRepresentation> ifNotFound) {
		
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(data);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpSelectUnionAll unionAll = crud.unionAll(Arrays.asList(copyIdToSearch, data), this, JnEntityDisposableRecords.INSTANCE);

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
	
}

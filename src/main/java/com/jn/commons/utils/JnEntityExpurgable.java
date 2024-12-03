package com.jn.commons.utils;

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
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.decorators.CcpEntityDelegator;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgableOptions;
import com.jn.commons.entities.JnEntityDisposableRecord;

public final class JnEntityExpurgable extends CcpEntityDelegator {

	private final CcpEntityExpurgableOptions timeOption;
	
	protected JnEntityExpurgable(CcpEntityExpurgableOptions timeOption, CcpEntity entity) {
		super(entity);
		this.timeOption = timeOption;
	}

	public final String calculateId(CcpJsonRepresentation json) {
		Long time = System.currentTimeMillis();
		String formattedCurrentDate = this.timeOption.getFormattedDate(time);

		ArrayList<Object> onlyPrimaryKeysValues = new ArrayList<>();
		onlyPrimaryKeysValues.add(formattedCurrentDate);
		ArrayList<Object> sortedPrimaryKeyValues = this.getSortedPrimaryKeyValues(json);
		onlyPrimaryKeysValues.addAll(sortedPrimaryKeyValues);
		
		String replace = onlyPrimaryKeysValues.toString().replace("[", "").replace("]", "");
		CcpHashDecorator hash2 = new CcpStringDecorator(replace).hash();
		String hash = hash2.asString("SHA1");
		return hash;
	}

	public CcpJsonRepresentation getCopyIdToSearch(CcpJsonRepresentation json) {
		
		String id = this.getPrimaryKeyValues(json).asUgglyJson();
		
		String entityName = this.getEntityName();
		
		CcpJsonRepresentation copyIdToSearch = CcpConstants.EMPTY_JSON.put("id", id).put("entity", entityName);
		return copyIdToSearch;
	}
	
	public final CcpBulkItem getRecordCopyToBulkOperation(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		CcpJsonRepresentation recordCopy = this.getRecordCopy(json);
		
		CcpBulkItem ccpBulkItem = new CcpBulkItem(recordCopy, operation, JnEntityDisposableRecord.ENTITY);
		
		return ccpBulkItem;
	}
	
	private boolean saveCopy(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation recordCopyToSave = this.getRecordCopy(json);
		
		boolean createdCopy = JnEntityDisposableRecord.ENTITY.create(recordCopyToSave);
		
		return createdCopy;
	}

	private CcpJsonRepresentation getRecordCopy(CcpJsonRepresentation json) {
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		String id = this.getPrimaryKeyValues(json).asUgglyJson();

		Long nextTimeStamp = this.timeOption.getNextTimeStamp();
		String nextDate = this.timeOption.getNextDate();
		CcpJsonRepresentation onlyExistingFields = this.getOnlyExistingFields(json);
		CcpJsonRepresentation recordCopyToSave = copyIdToSearch
				.put("timestamp", nextTimeStamp)
				.put("json", onlyExistingFields)
				.put("date", nextDate)
				.put("id", id)
				;
		return recordCopyToSave;
	}

	public boolean create(CcpJsonRepresentation json) {
		
		boolean created =  this.entity.create(json);
		
		this.saveCopy(json);
		return created;
	}

	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json, String id) {
		
		CcpJsonRepresentation createOrUpdate =  this.entity.createOrUpdate(json, id);
		
		boolean saveCopy = this.saveCopy(createOrUpdate);
		
		CcpJsonRepresentation put = createOrUpdate.put("createCopy", saveCopy);
		
		return put;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation createOrUpdate =  this.entity.createOrUpdate(json);
		
		boolean saveCopy = this.saveCopy(createOrUpdate);

		CcpJsonRepresentation put = createOrUpdate.put("createCopy", saveCopy);
		
		return put;
	}
	
	public boolean delete(CcpJsonRepresentation json) {
		
		boolean delete =  this.entity.delete(json);
		
		this.deleteCopy(json);

		return delete;
	}

	private boolean deleteCopy(CcpJsonRepresentation json) {
	
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		
		boolean deleteCopy = JnEntityDisposableRecord.ENTITY.delete(copyIdToSearch);
		
		return deleteCopy;
	}
	
	public boolean delete(String id) {
		
		boolean delete =  this.entity.delete(id);
		
		if(delete == false) {
			return false;
		}
		CcpJsonRepresentation json = this.getOneById(id);
		boolean deleteCopy = this.deleteCopy(json);
		return deleteCopy;
	}
	
	public boolean exists(CcpJsonRepresentation json) {
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation putAll = copyIdToSearch.putAll(json);
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, this, JnEntityDisposableRecord.ENTITY);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, json);
		
		if(isPresentInOriginalEntity) {
			return true;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.ENTITY.isPresentInThisUnionAll(unionAll, copyIdToSearch) == false;
		
		if(isNotPresentInCopyEntity) {
			return false;
		}
		
		CcpJsonRepresentation requiredEntityRow = JnEntityDisposableRecord.ENTITY.getRequiredEntityRow(unionAll, copyIdToSearch);
		Long timeStamp = requiredEntityRow.getAsLongNumber("timestamp");
		
		boolean obsoleteTimeStamp = timeStamp <= System.currentTimeMillis();
		
		if(obsoleteTimeStamp) {
			return false;
		}
		
		return true;
	}
	
	
	public CcpJsonRepresentation getOneById(CcpJsonRepresentation json) {
		
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		CcpJsonRepresentation putAll = copyIdToSearch.putAll(json);
		CcpSelectUnionAll searchResults = crud.unionAll(putAll, this, JnEntityDisposableRecord.ENTITY);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(searchResults, putAll);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = this.getRequiredEntityRow(searchResults, putAll);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.ENTITY.isPresentInThisUnionAll(searchResults, json) == false;

		if(isNotPresentInCopyEntity) {
			CcpJsonRepresentation oneById =  this.entity.getOneById(putAll);
			return oneById;
		}

		CcpJsonRepresentation requiredEntityRow = JnEntityDisposableRecord.ENTITY.getRequiredEntityRow(searchResults, putAll);
		Long timeStamp = requiredEntityRow.getAsLongNumber(JnEntityDisposableRecord.Fields.timestamp.name());
		
		boolean validTimeStamp = timeStamp > System.currentTimeMillis();
		
		if(validTimeStamp) {
			CcpJsonRepresentation innerJson = requiredEntityRow.getInnerJson(JnEntityDisposableRecord.Fields.json.name());
			return innerJson;
		}
		
		CcpJsonRepresentation oneById =  this.entity.getOneById(putAll);
		
		return oneById;
	}

	public CcpJsonRepresentation getOneById(CcpJsonRepresentation json, Function<CcpJsonRepresentation, CcpJsonRepresentation> ifNotFound) {
		
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation putAll = copyIdToSearch.putAll(json);
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, this, JnEntityDisposableRecord.ENTITY);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, json);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = this.getRequiredEntityRow(unionAll, json);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.ENTITY.isPresentInThisUnionAll(unionAll, copyIdToSearch) == false;

		if(isNotPresentInCopyEntity) {
			CcpJsonRepresentation oneById =  this.entity.getOneById(json, ifNotFound);
			return oneById;
		}

		CcpJsonRepresentation requiredEntityRow = JnEntityDisposableRecord.ENTITY.getRequiredEntityRow(unionAll, copyIdToSearch);
		Long timeStamp = requiredEntityRow.getAsLongNumber("timestamp");
		
		boolean validTimeStamp = timeStamp > System.currentTimeMillis();
		
		if(validTimeStamp) {
			return requiredEntityRow;
		}
		
		CcpJsonRepresentation oneById =  this.entity.getOneById(json, ifNotFound);
		return oneById;
	}
	
	public boolean isCopyableEntity() {
		return true;
	}

	public List<CcpJsonRepresentation> getParametersToSearch(CcpJsonRepresentation json) {
		List<CcpJsonRepresentation> mainParametersToSearch =  this.entity.getParametersToSearch(json);
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		List<CcpJsonRepresentation> othersParametersToSearch = JnEntityDisposableRecord.ENTITY.getParametersToSearch(copyIdToSearch);
		ArrayList<CcpJsonRepresentation> result = new ArrayList<>();
		result.addAll(othersParametersToSearch);
		result.addAll(mainParametersToSearch);
		return result;
	}

	public boolean isPresentInThisUnionAll(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {
		
		boolean presentInThisUnionAll =  this.entity.isPresentInThisUnionAll(unionAll, json);
		
		if(presentInThisUnionAll) {
			return true;
		}
		
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);

		boolean notFoundInDisposable = JnEntityDisposableRecord.ENTITY.isPresentInThisUnionAll(unionAll, copyIdToSearch) == false;
		
		if(notFoundInDisposable) {
			return false;
		}
		
		CcpJsonRepresentation requiredEntityRow = JnEntityDisposableRecord.ENTITY.getRequiredEntityRow(unionAll, copyIdToSearch);
		
		boolean valid = this.isValidTimestamp(requiredEntityRow);
		return valid;
	}

	private boolean isValidTimestamp(CcpJsonRepresentation requiredEntityRow) {
		
		String timeStampFieldName = JnEntityDisposableRecord.Fields.timestamp.name();
		
		boolean recordNotFound = requiredEntityRow.containsAllFields(timeStampFieldName) == false;
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
		
		CcpJsonRepresentation recordFromUnionAll =  this.entity.getRecordFromUnionAll(unionAll, json);
		
		boolean recordFound = recordFromUnionAll.isEmpty() == false;
		
		if(recordFound) {
			return recordFromUnionAll;
		}
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		CcpJsonRepresentation recordFromDisposable = JnEntityDisposableRecord.ENTITY.getRecordFromUnionAll(unionAll, copyIdToSearch);
		
		boolean isInvalid = this.isValidTimestamp(recordFromDisposable) == false;
	
		if(isInvalid) {
			return CcpConstants.EMPTY_JSON;
		}
		
		CcpJsonRepresentation innerJson = recordFromDisposable.getInnerJson(JnEntityDisposableRecord.Fields.json.name());
		return innerJson;
	}
}

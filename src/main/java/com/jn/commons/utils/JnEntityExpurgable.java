package com.jn.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.ccp.especifications.db.utils.CcpDbRequester;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.decorators.CcpEntityDelegator;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgableFactory;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgableOptions;
import com.ccp.exceptions.db.CcpEntityRecordNotFound;
import com.jn.commons.entities.JnEntityDisposableRecord;

public final class JnEntityExpurgable extends CcpEntityDelegator implements CcpEntityExpurgableFactory {

	private final CcpEntityExpurgableOptions timeOption;
	
	private JnEntityExpurgable() {
		super(null);
		this.timeOption = null;
	}
	
	protected JnEntityExpurgable(CcpEntity entity, CcpEntityExpurgableOptions timeOption) {
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
		String calculateId = this.calculateId(json);
		this.entity.createOrUpdate(json, calculateId);
		
		this.saveCopy(json);
		return true;
	}

	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json, String id) {

		CcpJsonRepresentation createOrUpdate =  this.entity.createOrUpdate(json, id);
		
		boolean saveCopy = this.saveCopy(json);
		
		CcpJsonRepresentation put = createOrUpdate.put("createCopy", saveCopy);
		
		return put;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json) {
		String calculateId = this.calculateId(json);
		
		CcpJsonRepresentation createOrUpdate = this.entity.createOrUpdate(json, calculateId);
		
		boolean saveCopy = this.saveCopy(json);

		CcpJsonRepresentation put = createOrUpdate.put("createCopy", saveCopy);
		
		return put;
	}
	
	public boolean delete(CcpJsonRepresentation json) {

		String calculateId = this.calculateId(json);
		
		boolean delete =  this.entity.delete(calculateId);
		
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
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, JnDeleteKeysFromCache.INSTANCE, this, JnEntityDisposableRecord.ENTITY);

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
		CcpSelectUnionAll searchResults = crud.unionAll(putAll, JnDeleteKeysFromCache.INSTANCE, this, JnEntityDisposableRecord.ENTITY);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(searchResults, putAll);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = this.getRequiredEntityRow(searchResults, putAll);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.ENTITY.isPresentInThisUnionAll(searchResults, json) == false;

		if(isNotPresentInCopyEntity) {
			String calculateId = this.calculateId(json);
			CcpJsonRepresentation oneById =  this.entity.getOneById(calculateId);
			return oneById;
		}

		CcpJsonRepresentation requiredEntityRow = JnEntityDisposableRecord.ENTITY.getRequiredEntityRow(searchResults, putAll);
		Long timeStamp = requiredEntityRow.getAsLongNumber(JnEntityDisposableRecord.Fields.timestamp.name());
		
		boolean validTimeStamp = timeStamp > System.currentTimeMillis();
		
		if(validTimeStamp) {
			CcpJsonRepresentation innerJson = requiredEntityRow.getInnerJson(JnEntityDisposableRecord.Fields.json.name());
			return innerJson;
		}

		String calculateId = this.calculateId(json);
		CcpJsonRepresentation oneById =  this.entity.getOneById(calculateId);
		
		return oneById;
	}

	public CcpJsonRepresentation getOneById(CcpJsonRepresentation json, Function<CcpJsonRepresentation, CcpJsonRepresentation> ifNotFound) {
		
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation putAll = copyIdToSearch.putAll(json);
		CcpSelectUnionAll unionAll = crud.unionAll(putAll, JnDeleteKeysFromCache.INSTANCE, this, JnEntityDisposableRecord.ENTITY);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, json);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = this.getRequiredEntityRow(unionAll, json);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.ENTITY.isPresentInThisUnionAll(unionAll, copyIdToSearch) == false;

		if(isNotPresentInCopyEntity) {
			CcpJsonRepresentation apply = ifNotFound.apply(json);
			return apply;
		}

		CcpJsonRepresentation requiredEntityRow = JnEntityDisposableRecord.ENTITY.getRequiredEntityRow(unionAll, copyIdToSearch);
		Long timeStamp = requiredEntityRow.getAsLongNumber("timestamp");
		
		boolean validTimeStamp = timeStamp > System.currentTimeMillis();
		
		if(validTimeStamp) {
			CcpJsonRepresentation innerJson = requiredEntityRow.getInnerJson("json");
			return innerJson;
		}
		CcpJsonRepresentation whenNotFound =  ifNotFound.apply(json);
		return whenNotFound;
	}
	
	public boolean isCopyableEntity() {
		return true;
	}
	
	private List<CcpJsonRepresentation> getParametersToSearchExpurgable(CcpJsonRepresentation json) {
		
		String id = this.calculateId(json);

		CcpDbRequester dependency = CcpDependencyInjection.getDependency(CcpDbRequester.class);
		
		String fieldNameToEntity = dependency.getFieldNameToEntity();
		String fieldNameToId = dependency.getFieldNameToId();
		
		String entityName = this.getEntityName();
		
		CcpJsonRepresentation mainRecord = CcpConstants.EMPTY_JSON
		.put(fieldNameToEntity, entityName)
		.put(fieldNameToId, id)
		;
		List<CcpJsonRepresentation> asList = Arrays.asList(mainRecord);
		return asList;
	}


	public List<CcpJsonRepresentation> getParametersToSearch(CcpJsonRepresentation json) {
		List<CcpJsonRepresentation> mainParametersToSearch =  this.getParametersToSearchExpurgable(json);
		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		List<CcpJsonRepresentation> othersParametersToSearch = JnEntityDisposableRecord.ENTITY.getParametersToSearch(copyIdToSearch);
		ArrayList<CcpJsonRepresentation> result = new ArrayList<>();
		result.addAll(othersParametersToSearch);
		result.addAll(mainParametersToSearch);
		return result;
	}

	public boolean isPresentInThisUnionAll(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {

		String id = this.calculateId(json);

		String entityName = this.getEntityName();
		
		boolean presentInThisUnionAll = unionAll.isPresent(entityName, id);

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
	
		String id = this.calculateId(json);
		String index = this.getEntityName();
		
		CcpJsonRepresentation recordFromUnionAll = unionAll.getEntityRow(index, id);

		boolean recordFound = recordFromUnionAll.isEmpty() == false;
		
		if(recordFound) {
			return recordFromUnionAll;
		}

		CcpJsonRepresentation copyIdToSearch = this.getCopyIdToSearch(json);
		CcpJsonRepresentation recordFromDisposable = JnEntityDisposableRecord.ENTITY.getRecordFromUnionAll(unionAll, copyIdToSearch);
		
		boolean isInvalid = this.isValidTimestamp(recordFromDisposable) == false;
	
		if(isInvalid) {
			throw new CcpEntityRecordNotFound(this, json);
		}
		
		CcpJsonRepresentation innerJson = recordFromDisposable.getInnerJson(JnEntityDisposableRecord.Fields.json.name());
		return innerJson;
	}
	
	public CcpJsonRepresentation getRequiredEntityRow(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {
		CcpJsonRepresentation recordFromUnionAll = this.getRecordFromUnionAll(unionAll, json);
		return recordFromUnionAll;
	}

	public CcpEntity getEntity(CcpEntity entity, CcpEntityExpurgableOptions timeOption) {
		JnEntityExpurgable jnEntityExpurgable = new JnEntityExpurgable(entity, timeOption);
		return jnEntityExpurgable;
	}	
}

package com.jn.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.ccp.constantes.CcpOtherConstants;
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
import com.ccp.utils.CcpHashAlgorithm;
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

	private final String getId(CcpJsonRepresentation json) {
		Long timestamp = json.getOrDefault(JnEntityDisposableRecord.Fields.timestamp.name(), System.currentTimeMillis());
		String formattedTimestamp = this.timeOption.getFormattedDate(timestamp);

		ArrayList<Object> onlyPrimaryKeysValues = new ArrayList<>();
		onlyPrimaryKeysValues.add(formattedTimestamp);
		ArrayList<Object> sortedPrimaryKeyValues = this.getSortedPrimaryKeyValues(json);
		onlyPrimaryKeysValues.addAll(sortedPrimaryKeyValues);
		
		String replace = onlyPrimaryKeysValues.toString().replace("[", "").replace("]", "");
		CcpHashDecorator hash2 = new CcpStringDecorator(replace).hash();
		String hash = hash2.asString(CcpHashAlgorithm.SHA1);
		return hash;
	}

	private CcpJsonRepresentation getExpurgableId(CcpJsonRepresentation json) {
		
		String id = this.getPrimaryKeyValues(json).asUgglyJson();
		
		String entityName = this.getEntityName();
		
		CcpJsonRepresentation expurgableId = CcpOtherConstants.EMPTY_JSON
				.put(JnEntityDisposableRecord.Fields.entity.name(), entityName)
				.put(JnEntityDisposableRecord.Fields.id.name(), id)
				;
		return expurgableId;
	}
	
	public CcpBulkItem toBulkItem(CcpJsonRepresentation json, CcpEntityOperationType operation) {

		String mainEntityId = this.getId(json);

		CcpBulkItem ccpBulkItem = new CcpBulkItem(json, operation, this, mainEntityId);

		return ccpBulkItem;
	}
	
	public final CcpBulkItem getRecordCopyToBulkOperation(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		
		CcpJsonRepresentation recordCopy = this.getExpurgable(json);
		
		String calculateId = JnEntityDisposableRecord.ENTITY.calculateId(recordCopy);
		
		CcpBulkItem ccpBulkItem = new CcpBulkItem(recordCopy, operation, JnEntityDisposableRecord.ENTITY, calculateId);
		
		return ccpBulkItem;
	}
	
	private JnEntityExpurgable saveExpurgable(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation recordCopyToSave = this.getExpurgable(json);
		
		JnEntityDisposableRecord.ENTITY.create(recordCopyToSave);
		
		return this;
	}

	private CcpJsonRepresentation getExpurgable(CcpJsonRepresentation json) {
		CcpJsonRepresentation onlyExistingFields = this.entity.getOnlyExistingFields(json);
		CcpJsonRepresentation expurgableId = this.getExpurgableId(json);
		String id = this.getPrimaryKeyValues(json).asUgglyJson();
		Long timestamp = json.getOrDefault(JnEntityDisposableRecord.Fields.timestamp.name(), System.currentTimeMillis());
		Long nextTimeStamp = this.timeOption.getNextTimeStamp(timestamp);
		String nextDate = this.timeOption.getNextDate(timestamp);
		CcpJsonRepresentation expurgable = expurgableId
				.put(JnEntityDisposableRecord.Fields.timestamp.name(), nextTimeStamp)
				.put(JnEntityDisposableRecord.Fields.json.name(), onlyExistingFields)
				.put(JnEntityDisposableRecord.Fields.date.name(), nextDate)
				.put(JnEntityDisposableRecord.Fields.id.name(), id)
				;
		return expurgable;
	}

	public boolean create(CcpJsonRepresentation json) {
		
		String mainEntityId = this.getId(json);
		
		CcpJsonRepresentation createOrUpdate = this.entity.createOrUpdate(json, mainEntityId);
		
		this.saveExpurgable(createOrUpdate);

		return true;
	}

	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json, String id) {

		CcpJsonRepresentation createOrUpdate =  this.entity.createOrUpdate(json, id);
		
		this.saveExpurgable(createOrUpdate);
		
		return createOrUpdate;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json) {
		
		String calculateId = this.getId(json);
		
		CcpJsonRepresentation createOrUpdate = this.entity.createOrUpdate(json, calculateId);
		
		return createOrUpdate;
	}
	
	public CcpJsonRepresentation delete(CcpJsonRepresentation json) {
		
		String calculateId = this.getId(json);
		
		this.entity.delete(calculateId);
		
		this.deleteCopy(json);

		return json;
	}

	private boolean deleteCopy(CcpJsonRepresentation json) {
	
		CcpJsonRepresentation expurgableId = this.getExpurgableId(json);
		
		CcpJsonRepresentation allValuesTogether = expurgableId.putAll(json);
		
		JnEntityDisposableRecord.ENTITY.delete(allValuesTogether);
		
		return true;
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
		
		CcpJsonRepresentation expurgableId = this.getExpurgableId(json);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation allValuesTogether = expurgableId.putAll(json);
		CcpSelectUnionAll unionAll = crud.unionAll(allValuesTogether, JnDeleteKeysFromCache.INSTANCE, this, JnEntityDisposableRecord.ENTITY);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, allValuesTogether);
		
		if(isPresentInOriginalEntity) {
			return true;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.ENTITY.isPresentInThisUnionAll(unionAll, expurgableId) == false;
		
		if(isNotPresentInCopyEntity) {
			return false;
		}
		
		CcpJsonRepresentation requiredEntityRow = JnEntityDisposableRecord.ENTITY.getRequiredEntityRow(unionAll, expurgableId);
		Long timeStamp = requiredEntityRow.getAsLongNumber(JnEntityDisposableRecord.Fields.timestamp.name());
		
		boolean obsoleteTimeStamp = timeStamp <= System.currentTimeMillis();
		
		if(obsoleteTimeStamp) {
			return false;
		}
		
		return true;
	}
	
	
	public CcpJsonRepresentation getOneById(CcpJsonRepresentation json) {
		
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation expurgableId = this.getExpurgableId(json);
		CcpJsonRepresentation allValuesTogether = expurgableId.putAll(json);
		
		CcpSelectUnionAll unionAll = crud.unionAll(allValuesTogether, JnDeleteKeysFromCache.INSTANCE, this, JnEntityDisposableRecord.ENTITY);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, allValuesTogether);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = this.getRequiredEntityRow(unionAll, allValuesTogether);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.ENTITY.isPresentInThisUnionAll(unionAll, allValuesTogether) == false;

		if(isNotPresentInCopyEntity) {
			String calculateId = this.getId(allValuesTogether);
			CcpJsonRepresentation oneById =  this.entity.getOneById(calculateId);
			return oneById;
		}

		CcpJsonRepresentation requiredEntityRow = JnEntityDisposableRecord.ENTITY.getRequiredEntityRow(unionAll, allValuesTogether);
		Long timeStamp = requiredEntityRow.getAsLongNumber(JnEntityDisposableRecord.Fields.timestamp.name());
		
		boolean validTimeStamp = timeStamp > System.currentTimeMillis();
		
		if(validTimeStamp) {
			CcpJsonRepresentation innerJson = requiredEntityRow.getInnerJson(JnEntityDisposableRecord.Fields.json.name());
			return innerJson;
		}

		String calculateId = this.getId(allValuesTogether);
		CcpJsonRepresentation oneById =  this.entity.getOneById(calculateId);
		
		return oneById;
	}

	public CcpJsonRepresentation getOneById(CcpJsonRepresentation json, Function<CcpJsonRepresentation, CcpJsonRepresentation> ifNotFound) {
		
		CcpJsonRepresentation expurgableId = this.getExpurgableId(json);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpJsonRepresentation allValuesTogether = expurgableId.putAll(json);
		CcpSelectUnionAll unionAll = crud.unionAll(allValuesTogether, JnDeleteKeysFromCache.INSTANCE, this, JnEntityDisposableRecord.ENTITY);

		boolean isPresentInOriginalEntity = this.isPresentInThisUnionAll(unionAll, allValuesTogether);
		
		if(isPresentInOriginalEntity) {
			CcpJsonRepresentation requiredEntityRow = this.getRequiredEntityRow(unionAll, allValuesTogether);
			return requiredEntityRow;
		}
	
		boolean isNotPresentInCopyEntity = JnEntityDisposableRecord.ENTITY.isPresentInThisUnionAll(unionAll, allValuesTogether) == false;

		if(isNotPresentInCopyEntity) {
			CcpJsonRepresentation apply = ifNotFound.apply(allValuesTogether);
			return apply;
		}

		CcpJsonRepresentation requiredEntityRow = JnEntityDisposableRecord.ENTITY.getRequiredEntityRow(unionAll, allValuesTogether);
		Long timeStamp = requiredEntityRow.getAsLongNumber(JnEntityDisposableRecord.Fields.timestamp.name());
		
		boolean validTimeStamp = timeStamp > System.currentTimeMillis();
		
		if(validTimeStamp) {
			CcpJsonRepresentation innerJson = requiredEntityRow.getInnerJson(JnEntityDisposableRecord.Fields.json.name());
			return innerJson;
		}
		CcpJsonRepresentation whenNotFound =  ifNotFound.apply(allValuesTogether);
		return whenNotFound;
	}
	
	public boolean isCopyableEntity() {
		return true;
	}
	
	private List<CcpJsonRepresentation> getParametersToSearchExpurgable(CcpJsonRepresentation json) {
		
		String id = this.getId(json);

		CcpDbRequester dependency = CcpDependencyInjection.getDependency(CcpDbRequester.class);
		
		String fieldNameToEntity = dependency.getFieldNameToEntity();
		String fieldNameToId = dependency.getFieldNameToId();
		
		String entityName = this.getEntityName();
		
		CcpJsonRepresentation mainRecord = CcpOtherConstants.EMPTY_JSON
		.put(fieldNameToEntity, entityName)
		.put(fieldNameToId, id)
		;
		List<CcpJsonRepresentation> asList = Arrays.asList(mainRecord);
		return asList;
	}


	public List<CcpJsonRepresentation> getParametersToSearch(CcpJsonRepresentation json) {
		List<CcpJsonRepresentation> mainParametersToSearch =  this.getParametersToSearchExpurgable(json);
		CcpJsonRepresentation expurgableId = this.getExpurgableId(json);
		List<CcpJsonRepresentation> othersParametersToSearch = JnEntityDisposableRecord.ENTITY.getParametersToSearch(expurgableId);
		ArrayList<CcpJsonRepresentation> result = new ArrayList<>();
		result.addAll(othersParametersToSearch);
		result.addAll(mainParametersToSearch);
		return result;
	}

	public boolean isPresentInThisUnionAll(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {

		String id = this.getId(json);

		String entityName = this.getEntityName();
		
		boolean presentInThisUnionAll = unionAll.isPresent(entityName, id);

		if(presentInThisUnionAll) {
			return true;
		}
		
		CcpJsonRepresentation expurgableId = this.getExpurgableId(json);

		boolean notFoundInDisposable = JnEntityDisposableRecord.ENTITY.isPresentInThisUnionAll(unionAll, expurgableId) == false;
		
		if(notFoundInDisposable) {
			return false;
		}
		
		CcpJsonRepresentation requiredEntityRow = JnEntityDisposableRecord.ENTITY.getRequiredEntityRow(unionAll, expurgableId);
		
		boolean valid = this.isValidTimestamp(requiredEntityRow);
		
		if(valid) {
			return true;
		}
		
		return false;
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
	
		String id = this.getId(json);
		String index = this.getEntityName();
		
		CcpJsonRepresentation recordFromUnionAll = unionAll.getEntityRow(index, id);

		boolean recordFound = recordFromUnionAll.isEmpty() == false;
		
		if(recordFound) {
			return recordFromUnionAll;
		}

		CcpJsonRepresentation expurgableId = this.getExpurgableId(json);
		CcpJsonRepresentation recordFromDisposable = JnEntityDisposableRecord.ENTITY.getRecordFromUnionAll(unionAll, expurgableId);
		
		boolean isInvalid = this.isValidTimestamp(recordFromDisposable) == false;
	
		if(isInvalid) {
			throw new CcpEntityRecordNotFound(this, expurgableId);
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

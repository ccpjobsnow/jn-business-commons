package com.jn.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpBulkOperationResult;
import com.ccp.especifications.db.bulk.CcpDbBulkExecutor;
import com.ccp.especifications.db.bulk.CcpEntityBulkOperationType;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpHandleWithSearchResultsInTheEntity;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.jn.commons.actions.SaveMainEntity;
import com.ccp.jn.commons.actions.SaveSupportEntity;
import com.jn.commons.entities.JnEntityRecordToReprocess;


public class JnCommonsExecuteBulkOperation {

	public static final JnCommonsExecuteBulkOperation INSTANCE = new JnCommonsExecuteBulkOperation();
	
	private JnCommonsExecuteBulkOperation() {}
	
	public JnCommonsExecuteBulkOperation executeBulk(List<CcpJsonRepresentation> records, CcpEntityBulkOperationType operation, CcpEntity entity) {
		
		boolean emptyRecords = records.isEmpty();
		
		if(emptyRecords) {
			return this;
		}
		
		List<CcpBulkItem> collect = records.stream().map(json -> entity.getMainBulkItem(json, operation)).collect(Collectors.toList());
		
		JnCommonsExecuteBulkOperation executeBulk = this.executeBulk(collect);
		return executeBulk;
	}
	
	public JnCommonsExecuteBulkOperation executeBulk(CcpJsonRepresentation json, CcpEntity entity, CcpEntityBulkOperationType operation) {
		CcpEntity twinEntity = entity.getTwinEntity();
		CcpBulkItem bulkItem = entity.getMainBulkItem(json, operation);
		CcpBulkItem bulkItem2 = twinEntity.getMainBulkItem(json, operation);
		JnCommonsExecuteBulkOperation executeBulk = this.executeBulk(bulkItem, bulkItem2);
		return executeBulk;
	}
	
	public JnCommonsExecuteBulkOperation executeBulk(CcpBulkItem... items) {
		List<CcpBulkItem> asList = Arrays.asList(items);
		JnCommonsExecuteBulkOperation executeBulk = this.executeBulk(asList);
		return executeBulk;
	}
	
	public JnCommonsExecuteBulkOperation executeBulk(Collection<CcpBulkItem> items) {
		
		boolean emptyItems = items.isEmpty();
		
		if(emptyItems) {
			return this;
		}

		CcpDbBulkExecutor dbBulkExecutor = CcpDependencyInjection.getDependency(CcpDbBulkExecutor.class);
		
		for (CcpBulkItem item : items) {
			dbBulkExecutor = dbBulkExecutor.addRecord(item);
		}
 		JnCommonsExecuteBulkOperation commitAndSaveErrorsAndDeleteRecordsFromCache = this.commitAndSaveErrorsAndDeleteRecordsFromCache(dbBulkExecutor);
		return commitAndSaveErrorsAndDeleteRecordsFromCache;
	}
	
	private JnCommonsExecuteBulkOperation commitAndSaveErrorsAndDeleteRecordsFromCache(CcpDbBulkExecutor dbBulkExecutor) {

		List<CcpBulkOperationResult> allResults = dbBulkExecutor.getBulkOperationResult();
		List<CcpBulkOperationResult> errors = allResults.stream().filter(x -> x.hasError()).collect(Collectors.toList());
		List<CcpBulkItem> collect = errors.stream().map(x -> x.getReprocess(ReprocessMapper.INSTANCE, JnEntityRecordToReprocess.ENTITY)).collect(Collectors.toList());
		this.executeBulk(collect);
		JnCommonsExecuteBulkOperation deleteKeysFromCache = this.deleteKeysFromCache(allResults);
		return deleteKeysFromCache; 
	}

	private JnCommonsExecuteBulkOperation deleteKeysFromCache(List<CcpBulkOperationResult> allResults) {
		Set<String> keysToDeleteInCache = new ArrayList<>(allResults).stream()
		.filter(x -> x.hasError() == false)
		.map(x -> x.getCacheKey())
		.collect(Collectors.toSet());
		String[] array = keysToDeleteInCache.toArray(new String[keysToDeleteInCache.size()]);
		
		JnDeleteKeysFromCache.INSTANCE.accept(array);
		return this;
	}
	
	public CcpSelectUnionAll changeStatus(CcpJsonRepresentation json, CcpEntity entity) {
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpSelectUnionAll unionAll = crud.unionBetweenMainAndTwinEntities(json, JnDeleteKeysFromCache.INSTANCE, entity);
		CcpEntity twinEntity = entity.getTwinEntity();
		CcpBulkItem twin = twinEntity.toBulkItemToCreateOrDelete(unionAll, json);
		CcpBulkItem main = entity.toBulkItemToCreateOrDelete(unionAll, json);
		
		this.executeBulk(main, twin);
		return unionAll;
	}
	
	@SuppressWarnings("unchecked")
	public CcpSelectUnionAll executeSelectUnionAllThenExecuteBulkOperation(CcpJsonRepresentation json,  CcpHandleWithSearchResultsInTheEntity<List<CcpBulkItem>> ... handlers) {
		Set<CcpEntity> collect = Arrays.asList(handlers).stream().map(x -> x.getEntityToSearch()).collect(Collectors.toSet());
		CcpEntity[] array = collect.toArray(new CcpEntity[collect.size()]);
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		CcpSelectUnionAll unionAll = crud.unionAll(json, JnDeleteKeysFromCache.INSTANCE, array);
		
		Set<CcpBulkItem> all = new HashSet<>();
		
		for (CcpHandleWithSearchResultsInTheEntity<List<CcpBulkItem>> handler : handlers) {
			List<CcpBulkItem> list =  unionAll.handleRecordInUnionAll(json, handler);
			all.addAll(list);
		}
		this.executeBulk(all);

		CcpJsonRepresentation data = json;
	
		for (CcpHandleWithSearchResultsInTheEntity<List<CcpBulkItem>> handler : handlers) {
			
			CcpEntity entityToSearch = handler.getEntityToSearch();
			
			boolean presentInThisUnionAll = entityToSearch.isPresentInThisUnionAll(unionAll, data);
			
			if(presentInThisUnionAll) {
				Function<CcpJsonRepresentation, CcpJsonRepresentation> doAfterSavingIfRecordIsFound = handler.doAfterSavingIfRecordIsFound();
				data = doAfterSavingIfRecordIsFound.apply(data);
				continue;
			}
			Function<CcpJsonRepresentation, CcpJsonRepresentation> doAfterSavingIfRecordIsNotFound = handler.doAfterSavingIfRecordIsNotFound();
			data = doAfterSavingIfRecordIsNotFound.apply(data);
		}
		
		return unionAll;
	}

	@SuppressWarnings("unchecked")
	public CcpJsonRepresentation executeSelectUnionAllThenSaveInTheMainAndTwinEntities(CcpJsonRepresentation json, 
			CcpEntity mainEntity, Function<CcpJsonRepresentation, CcpJsonRepresentation> whenPresentInMainEntityOrIsNewRecord) {
		
		CcpEntity supportEntity = mainEntity.getTwinEntity();
		
		SaveMainEntity saveMainEntity = new SaveMainEntity(mainEntity);
		
		SaveSupportEntity saveSupportEntity = new SaveSupportEntity(supportEntity);
		
		CcpHandleWithSearchResultsInTheEntity<List<CcpBulkItem>>[] array = new CcpHandleWithSearchResultsInTheEntity[]{
				saveMainEntity,
				saveSupportEntity
		};
		
		CcpSelectUnionAll result = this.executeSelectUnionAllThenExecuteBulkOperation(json, array);
		
		boolean isPresentInMainEntity = mainEntity.isPresentInThisUnionAll(result, json);
		
		if(isPresentInMainEntity) {
			CcpJsonRepresentation apply = whenPresentInMainEntityOrIsNewRecord.apply(json);
			return apply;
		}
		
		boolean isNewRecord = false == supportEntity.isPresentInThisUnionAll(result, json);

		if(isNewRecord) {
			CcpJsonRepresentation apply = whenPresentInMainEntityOrIsNewRecord.apply(json);
			return apply;
		}
			
		return json;
	}

	public JnCommonsExecuteBulkOperation executeBulk(CcpJsonRepresentation json, CcpEntityBulkOperationType operation, CcpEntity...entities) {
		
		List<CcpBulkItem> items = new ArrayList<>();
 		
		for (CcpEntity entity : entities) {
			List<CcpBulkItem> bulkItems = entity.toBulkItems(json, operation);
			for (CcpBulkItem bulkItem : bulkItems) {
				items.add(bulkItem);
			}			
		}
		
		JnCommonsExecuteBulkOperation executeBulk = this.executeBulk(items);
		return executeBulk;
	}
}

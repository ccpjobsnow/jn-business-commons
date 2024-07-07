package com.jn.commons.entities.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.decorators.CcpTimeDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpDbRequester;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;

public abstract class JnBaseEntity implements CcpEntity{

	final String name = this.getEntityName();
	final CcpEntityField[] fields;
	
	protected JnBaseEntity(CcpEntityField... fields) {
		this.fields = fields;
	}
	
	protected ArrayList<Object> getSortedPrimaryKeyValues(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation primaryKeyValues = this.getPrimaryKeyValues(json);
		
		List<String> primaryKeyNames = this.getPrimaryKeyNames();
		
		Set<String> missingKeys = primaryKeyValues.getMissingFields(primaryKeyNames);

		boolean isMissingKeys = missingKeys.isEmpty() == false;
		
		if(isMissingKeys) {
			throw new RuntimeException("It is missing the keys '" + missingKeys + "' from entity '" + this + "' in the object " + json );
		}
		
		TreeMap<String, Object> treeMap = new TreeMap<>(primaryKeyValues.content);
		Collection<Object> values2 = treeMap.values();
		ArrayList<Object> onlyPrimaryKeys = new ArrayList<>(values2);
		return onlyPrimaryKeys;
	}

	public final CcpJsonRepresentation getPrimaryKeyValues(CcpJsonRepresentation json) {
		
		List<String> onlyPrimaryKey = this.getPrimaryKeyNames();
		CcpJsonRepresentation jsonPiece = json.getJsonPiece(onlyPrimaryKey);
		return jsonPiece;
	}

	public String getEntityName() {
		String simpleName = this.getClass().getSimpleName();
		String snackCase = new CcpStringDecorator(simpleName).text().toSnakeCase().content;
		String substring = snackCase.substring(snackCase.indexOf("entity") + 7);
		return substring;
	}

	public final CcpEntityField[] getFields() {
		return this.fields;
	}

	public String toString() {
		String entityName = this.getEntityName();
		return entityName;
	}
	
	protected List<CcpBulkItem> toCreateBulkItems(String... jsons){
		List<CcpBulkItem> collect = Arrays.asList(jsons)
		.stream().map(x -> new CcpJsonRepresentation(x))
		.map(x -> new CcpBulkItem(x, CcpEntityOperationType.create, this))
		.collect(Collectors.toList());
		return collect;
	}
	
	public boolean canSaveCopy() {
		List<String> primaryKeyNames = this.getPrimaryKeyNames();
		int size = primaryKeyNames.size();
		boolean entityHasJustPrimaryKeys = size < this.fields.length;
		return entityHasJustPrimaryKeys;
	}
	
	public final int hashCode() {
		String entityName = this.getEntityName();
		return entityName.hashCode();
	}
	
	public final boolean equals(Object obj) {
		try {
			String entityName = ((JnBaseEntity)obj).getEntityName();
			String entityName2 = this.getEntityName();
			boolean equals = entityName.equals(entityName2);
			return equals;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean hasMirrorEntity() {
		return false;
	}
	public List<CcpJsonRepresentation> getParametersToSearch(CcpJsonRepresentation json) {
		
		CcpDbRequester dependency = CcpDependencyInjection.getDependency(CcpDbRequester.class);
		
		String fieldNameToEntity = dependency.getFieldNameToEntity();
		String fieldNameToId = dependency.getFieldNameToId();
		
		String entityName = this.getEntityName();
		String id = this.calculateId(json);
		
		CcpJsonRepresentation mainRecord = CcpConstants.EMPTY_JSON
		.put(fieldNameToEntity, entityName)
		.put(fieldNameToId, id)
		;
		List<CcpJsonRepresentation> asList = Arrays.asList(mainRecord);
		return asList;
	}

	public boolean isPresentInThisUnionAll(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {
		
		String index = this.getEntityName();
		String id = this.calculateId(json);

		boolean present = unionAll.isPresent(index, id);
		
		return present;
	}
	
	public CcpJsonRepresentation getRecordFromUnionAll(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {

		String id = this.calculateId(json);
		String index = this.getEntityName();
		
		CcpJsonRepresentation jsonValue = unionAll.getEntityRow(index, id);
		
		return jsonValue;
	}
	
	public CcpEntity fromCache() {
		JnCacheEntity jnCacheEntity = new JnCacheEntity(this);
		return jnCacheEntity;
	}

	public CcpJsonRepresentation addTimeFields(CcpJsonRepresentation json) {
		CcpTimeDecorator ctd = new CcpTimeDecorator();
		String formattedDateTime = ctd.getFormattedDateTime("dd/MM/yyyy HH:mm:ss.SSS");
		boolean containsAllFields = json.containsAllFields("timestamp");
		
		if(containsAllFields) {
			return json;
		}
		
		CcpJsonRepresentation put = json.put("timestamp", ctd.time).put("date", formattedDateTime);
		
		return put;
	}
	
	public boolean create(CcpJsonRepresentation json) {
		CcpJsonRepresentation addTimeFields = this.addTimeFields(json);
		boolean create = CcpEntity.super.create(addTimeFields);
		return create;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json) {
		CcpJsonRepresentation addTimeFields = this.addTimeFields(json);
		CcpJsonRepresentation createOrUpdate = CcpEntity.super.createOrUpdate(addTimeFields);
		return createOrUpdate;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json, String id) {
		CcpJsonRepresentation addTimeFields = this.addTimeFields(json);
		CcpJsonRepresentation createOrUpdate = CcpEntity.super.createOrUpdate(addTimeFields, id);
		return createOrUpdate;
	}
	
	public CcpBulkItem toBulkItem(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		CcpJsonRepresentation addTimeFields = this.addTimeFields(json);
		CcpBulkItem bulkItem = CcpEntity.super.toBulkItem(addTimeFields, operation);
		return bulkItem;
	}
}

package com.jn.commons.entities.base;

import java.util.List;
import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;

public class JnCacheEntity implements CcpEntity {

	private final CcpEntity entity;

	protected JnCacheEntity(CcpEntity entity) {
		this.entity = entity;
	}

	public List<CcpJsonRepresentation> getParametersToSearch(CcpJsonRepresentation json) {
		return this.entity.getParametersToSearch(json);
	}

	public boolean isPresentInThisUnionAll(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {
		return this.entity.isPresentInThisUnionAll(unionAll, json);
	}

	public CcpJsonRepresentation getRecordFromUnionAll(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {
		return this.entity.getRecordFromUnionAll(unionAll, json);
	}

	public String getEntityName() {
		return this.entity.getEntityName();
	}

	public String getId(CcpJsonRepresentation values) {
		return this.entity.getId(values);
	}

	public CcpJsonRepresentation getPrimaryKeyValues(CcpJsonRepresentation values) {
		return this.entity.getPrimaryKeyValues(values);
	}

	public CcpBulkItem getRecordToBulkOperation(CcpJsonRepresentation values, CcpEntityOperationType operation) {
		return this.entity.getRecordToBulkOperation(values, operation);
	}

	public CcpEntityField[] getFields() {
		return this.entity.getFields();
	}

	public boolean canSaveCopy() {
		return this.entity.canSaveCopy();
	}

	public boolean hasMirrorEntity() {
		return this.entity.hasMirrorEntity();
	}

	public CcpBulkItem toBulkItem(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		return this.entity.toBulkItem(json, operation);
	}

	public CcpEntity getMirrorEntity() {
		return this.entity.getMirrorEntity();
	}

	public CcpJsonRepresentation getOneById(CcpJsonRepresentation data,
			Function<CcpJsonRepresentation, CcpJsonRepresentation> ifNotFound) {
		return this.entity.getOneById(data, ifNotFound);
	}

	public CcpJsonRepresentation getOneById(CcpJsonRepresentation data) {
		return this.entity.getOneById(data);
	}

	public CcpJsonRepresentation getOneById(String id) {
		return this.entity.getOneById(id);
	}

	public boolean exists(String id) {
		return this.entity.exists(id);
	}

	public boolean exists(CcpJsonRepresentation data) {
		return this.entity.exists(data);
	}

	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation values) {
		return this.entity.createOrUpdate(values);
	}

	public boolean create(CcpJsonRepresentation values) {
		return this.entity.create(values);
	}

	public boolean delete(CcpJsonRepresentation values) {
		return this.entity.delete(values);
	}

	public boolean delete(String id) {
		return this.entity.delete(id);
	}

	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation data, String id) {
		return this.entity.createOrUpdate(data, id);
	}

	public List<CcpBulkItem> getFirstRecordsToInsert() {
		return this.entity.getFirstRecordsToInsert();
	}

	public CcpJsonRepresentation getOnlyExistingFields(CcpJsonRepresentation values) {
		return this.entity.getOnlyExistingFields(values);
	}

	public List<String> getPrimaryKeyNames() {
		return this.entity.getPrimaryKeyNames();
	}
	
	
	
}

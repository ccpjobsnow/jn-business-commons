package com.jn.commons.entities.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpHashDecorator;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.decorators.CcpTimeDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.JnEntityAudit;

public final class JnAuditableEntity implements CcpEntity {
	
	private final CcpEntity entity;
	
	protected JnAuditableEntity(CcpEntity entity) {
		this.entity = entity;
	}
	private final void saveAuditory(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		boolean canNotSaveCopy = this.entity.isCopyableEntity() == false;
		if(canNotSaveCopy) {
			return;
		}
		CcpJsonRepresentation audit = this.getAuditRecord(json, operation);
		CcpCrud dependency = CcpDependencyInjection.getDependency(CcpCrud.class);
		dependency.createOrUpdate(JnEntityAudit.ENTITY, audit);
	}

	public final CcpBulkItem getRecordCopyToBulkOperation(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		
		CcpJsonRepresentation audit = this.getAuditRecord(json, operation);
		CcpBulkItem ccpBulkItem = new CcpBulkItem(audit, CcpEntityOperationType.create, JnEntityAudit.ENTITY);
		return ccpBulkItem;
	}

	public final String calculateId(CcpJsonRepresentation json) {

		List<String> primaryKeyNames = this.entity.getPrimaryKeyNames();
		
		boolean hasNoPrimaryKey = primaryKeyNames.isEmpty();
		
		if(hasNoPrimaryKey) {
			String string = UUID.randomUUID().toString();
			String hash = new CcpStringDecorator(string).hash().asString("SHA1");
			return hash;
		}
		
		ArrayList<Object> sortedPrimaryKeyValues = this.getSortedPrimaryKeyValues(json);
		
		String replace = sortedPrimaryKeyValues.toString().replace("[", "").replace("]", "");
		CcpHashDecorator hash2 = new CcpStringDecorator(replace).hash();
		String hash = hash2.asString("SHA1");
		return hash;
	}
	
	private CcpJsonRepresentation getAuditRecord(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		String id = this.entity.getPrimaryKeyValues(json).asUgglyJson();
		String entityName = this.entity.getEntityName();
		CcpJsonRepresentation onlyExistingFields = this.entity.getOnlyExistingFields(json);
		CcpTimeDecorator ctd = new CcpTimeDecorator();
		String formattedDateTime = ctd.getFormattedDateTime("dd/MM/yyyy HH:mm:ss.SSS");
		CcpJsonRepresentation audit = 
				CcpConstants.EMPTY_JSON
				.put("timestamp", System.currentTimeMillis())
				.put("json", "" + onlyExistingFields)
				.put("date", formattedDateTime)
				.put("operation", operation)
				.put("entity", entityName)
				.put("id", id)
		;
		return audit;
	}
	

	public boolean delete(String id) {
		boolean delete = this.entity.delete(id);
		CcpJsonRepresentation oneById = this.entity.getOneById(id);
		this.saveAuditory(oneById, CcpEntityOperationType.delete);
		return delete;
	}

	public boolean delete(CcpJsonRepresentation json) {
		boolean delete = this.entity.delete(json);
		this.saveAuditory(json, CcpEntityOperationType.delete);
		return delete;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json) {
		CcpJsonRepresentation createOrUpdate = this.entity.createOrUpdate(json);
		try {
			boolean exists = this.entity.exists(json);
			CcpEntityOperationType operation = exists ? CcpEntityOperationType.create : CcpEntityOperationType.update;
			this.saveAuditory(createOrUpdate, operation);
			return createOrUpdate;
			
		} catch (Exception e) {
			return createOrUpdate;
		}
	}
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json, String id) {
		CcpJsonRepresentation createOrUpdate = this.entity.createOrUpdate(json, id);
		boolean exists = this.entity.exists(id);
		CcpEntityOperationType operation = exists ? CcpEntityOperationType.create : CcpEntityOperationType.update;
		this.saveAuditory(createOrUpdate, operation);
		return createOrUpdate;
	}

	public boolean create(CcpJsonRepresentation json) {
		boolean created = this.entity.create(json);
		CcpEntityOperationType operation = created ? CcpEntityOperationType.create : CcpEntityOperationType.update;
		this.saveAuditory(json, operation);
		return created;
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
	public CcpJsonRepresentation getPrimaryKeyValues(CcpJsonRepresentation json) {
		return this.entity.getPrimaryKeyValues(json);
	}
	public CcpEntityField[] getFields() {
		return this.entity.getFields();
	}
	public boolean isCopyableEntity() {
		return this.entity.isCopyableEntity();
	}
	public boolean hasTwinEntity() {
		return this.entity.hasTwinEntity();
	}
	public CcpBulkItem toBulkItem(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		return this.entity.toBulkItem(json, operation);
	}
	public CcpEntity getTwinEntity() {
		return this.entity.getTwinEntity();
	}
	public CcpJsonRepresentation getOneById(CcpJsonRepresentation json,
			Function<CcpJsonRepresentation, CcpJsonRepresentation> ifNotFound) {
		return this.entity.getOneById(json, ifNotFound);
	}
	public CcpJsonRepresentation getOneById(CcpJsonRepresentation json) {
		return this.entity.getOneById(json);
	}
	public CcpJsonRepresentation getOneById(String id) {
		return this.entity.getOneById(id);
	}
	public boolean exists(String id) {
		return this.entity.exists(id);
	}
	public boolean exists(CcpJsonRepresentation json) {
		return this.entity.exists(json);
	}
	public CcpJsonRepresentation getOnlyExistingFields(CcpJsonRepresentation json) {
		return this.entity.getOnlyExistingFields(json);
	}
	public List<String> getPrimaryKeyNames() {
		return this.entity.getPrimaryKeyNames();
	}
	public boolean isVirtualEntity() {
		return this.entity.isVirtualEntity();
	}
	public CcpJsonRepresentation getRequiredEntityRow(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {
		return this.entity.getRequiredEntityRow(unionAll, json);
	}
	public boolean isPresentInThisJsonInMainEntity(CcpJsonRepresentation json) {
		return this.entity.isPresentInThisJsonInMainEntity(json);
	}
	public CcpJsonRepresentation getInnerJsonFromMainAndTwinEntities(CcpJsonRepresentation json) {
		return this.entity.getInnerJsonFromMainAndTwinEntities(json);
	}
	public CcpJsonRepresentation getData(CcpJsonRepresentation json) {
		return this.entity.getData(json);
	}
	public String[] getEntitiesToSelect() {
		return this.entity.getEntitiesToSelect();
	}

	
	
}

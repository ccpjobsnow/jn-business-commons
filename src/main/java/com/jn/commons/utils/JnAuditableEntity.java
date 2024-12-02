package com.jn.commons.utils;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpTimeDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.decorators.CcpDelegatorEntity;
import com.jn.commons.entities.JnEntityAudit;

public final class JnAuditableEntity extends CcpDelegatorEntity {
	
	protected JnAuditableEntity(CcpEntity entity) {
		super(entity);
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
}

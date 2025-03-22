package com.jn.commons.utils;

import com.ccp.constantes.CcpOtherConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityBulkOperationType;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.decorators.CcpEntityDecoratorFactory;
import com.ccp.especifications.db.utils.decorators.CcpEntityDelegator;
import com.jn.commons.entities.JnEntityAudit;

public final class JnEntityVersionable extends CcpEntityDelegator implements CcpEntityDecoratorFactory {
	
	private JnEntityVersionable() {
		super(null);
	}
	
	protected JnEntityVersionable(CcpEntity entity) {
		super(entity);
	}
	
	private final JnEntityVersionable saveAuditory(CcpJsonRepresentation json, CcpEntityBulkOperationType operation) {
		
		boolean canNotSaveCopy = this.entity.isCopyableEntity() == false;
	
		if(canNotSaveCopy) {
			return this;
		}
		
		CcpJsonRepresentation audit = this.getAuditRecord(json, operation);
		CcpCrud dependency = CcpDependencyInjection.getDependency(CcpCrud.class);
		String calculateId = JnEntityAudit.ENTITY.calculateId(audit);
		String auditEntity = JnEntityAudit.ENTITY.getEntityName();
		dependency.createOrUpdate(auditEntity, audit, calculateId);
		return this;
	}

	public final CcpBulkItem getRecordCopyToBulkOperation(CcpJsonRepresentation json, CcpEntityBulkOperationType operation) {
		
		CcpJsonRepresentation audit = this.getAuditRecord(json, operation);
		CcpBulkItem ccpBulkItem = JnEntityAudit.ENTITY.toBulkItem(audit, CcpEntityBulkOperationType.create);
		return ccpBulkItem;
	}

	private CcpJsonRepresentation getAuditRecord(CcpJsonRepresentation json, CcpEntityBulkOperationType operation) {
		CcpJsonRepresentation oneById = this.entity.getOneById(json, x -> json);
		String id = this.entity.getPrimaryKeyValues(json).asUgglyJson();
		String entityName = this.entity.getEntityName();
		CcpJsonRepresentation audit = 
				CcpOtherConstants.EMPTY_JSON
				.put(JnEntityAudit.Fields.operation.name(), operation)
				.put(JnEntityAudit.Fields.entity.name(), entityName)
				.put(JnEntityAudit.Fields.json.name(), "" + oneById)
				.put(JnEntityAudit.Fields.id.name(), id)
		;
		return audit;
	}
	

	public boolean delete(String id) {
		boolean delete = this.entity.delete(id);
		CcpJsonRepresentation oneById = this.entity.getOneById(id);
		this.saveAuditory(oneById, CcpEntityBulkOperationType.delete);
		return delete;
	}

	public CcpJsonRepresentation delete(CcpJsonRepresentation json) {
		CcpJsonRepresentation delete = this.entity.delete(json);
		this.saveAuditory(json, CcpEntityBulkOperationType.delete);
		return delete;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation createOrUpdate = this.entity.createOrUpdate(json);
		
		try {
			boolean exists = this.entity.exists(json);
			CcpEntityBulkOperationType operation = exists ? CcpEntityBulkOperationType.create : CcpEntityBulkOperationType.update;
			this.saveAuditory(json, operation);
			return createOrUpdate;
			
		} catch (Exception e) {
			return createOrUpdate;
		}
	}
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json, String id) {
		CcpJsonRepresentation createOrUpdate = this.entity.createOrUpdate(json, id);
		boolean exists = this.entity.exists(id);
		CcpEntityBulkOperationType operation = exists ? CcpEntityBulkOperationType.create : CcpEntityBulkOperationType.update;
		this.saveAuditory(json, operation);
		return createOrUpdate;
	}

	public boolean isPresentInThisUnionAll(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {
		boolean presentInThisUnionAll = this.entity.isPresentInThisUnionAll(unionAll, json);
		return presentInThisUnionAll;
	}

	public CcpEntity getEntity(CcpEntity entity) {
		JnEntityVersionable jnEntityVersionable = new JnEntityVersionable(entity);
		return jnEntityVersionable;
	}
}

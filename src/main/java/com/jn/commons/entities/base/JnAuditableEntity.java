package com.jn.commons.entities.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.JnEntityAudit;

public abstract class JnAuditableEntity extends JnBaseEntity{
	
	protected JnAuditableEntity(CcpEntityField[] fields) {
		super(fields);
	}
	private final void saveAuditory(CcpJsonRepresentation values, CcpEntityOperationType operation) {
		boolean canNotSaveCopy = this.canSaveCopy() == false;
		if(canNotSaveCopy) {
			return;
		}
		CcpJsonRepresentation audit = this.getAuditRecord(values, operation);
		CcpCrud dependency = CcpDependencyInjection.getDependency(CcpCrud.class);
		dependency.createOrUpdate(JnEntityAudit.INSTANCE, audit);
	}

	public final CcpBulkItem getRecordToBulkOperation(CcpJsonRepresentation values, CcpEntityOperationType operation) {
		
		CcpJsonRepresentation audit = this.getAuditRecord(values, operation);
		CcpBulkItem ccpBulkItem = new CcpBulkItem(audit, CcpEntityOperationType.create, JnEntityAudit.INSTANCE);
		return ccpBulkItem;
	}

	public final String getId(CcpJsonRepresentation values) {

		List<String> primaryKeyNames = this.getPrimaryKeyNames();
		
		boolean hasNoPrimaryKey = primaryKeyNames.isEmpty();
		
		if(hasNoPrimaryKey) {
			String string = UUID.randomUUID().toString();
			String hash = new CcpStringDecorator(string).hash().asString("SHA1");
			return hash;
		}
		
		ArrayList<Object> sortedPrimaryKeyValues = this.getSortedPrimaryKeyValues(values);
		
		String replace = sortedPrimaryKeyValues.toString().replace("[", "").replace("]", "");
		String hash = new CcpStringDecorator(replace).hash().asString("SHA1");
		return hash;
	}
	
	private CcpJsonRepresentation getAuditRecord(CcpJsonRepresentation values, CcpEntityOperationType operation) {
		String id = this.getId(values);
		String entityName = this.getEntityName();
		CcpJsonRepresentation onlyExistingFields = this.getOnlyExistingFields(values);
		CcpJsonRepresentation audit = 
				CcpConstants.EMPTY_JSON
				.put("timestamp", System.currentTimeMillis())
				.put("json", "" + onlyExistingFields)
				.put("operation", operation)
				.put("entity", entityName)
				.put("id", id)
		;
		return audit;
	}
	

	public boolean delete(String id) {
		boolean delete = super.delete(id);
		CcpJsonRepresentation oneById = this.getOneById(id);
		this.saveAuditory(oneById, CcpEntityOperationType.delete);
		return delete;
	}

	public boolean delete(CcpJsonRepresentation values) {
		boolean delete = super.delete(values);
		this.saveAuditory(values, CcpEntityOperationType.delete);
		return delete;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation values) {
		CcpJsonRepresentation createOrUpdate = super.createOrUpdate(values);
		try {
			boolean exists = this.exists(values);
			CcpEntityOperationType operation = exists ? CcpEntityOperationType.create : CcpEntityOperationType.update;
			this.saveAuditory(createOrUpdate, operation);
			return createOrUpdate;
			
		} catch (Exception e) {
			return createOrUpdate;
		}
	}
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation data, String id) {
		CcpJsonRepresentation createOrUpdate = super.createOrUpdate(data, id);
		boolean exists = this.exists(id);
		CcpEntityOperationType operation = exists ? CcpEntityOperationType.create : CcpEntityOperationType.update;
		this.saveAuditory(createOrUpdate, operation);
		return createOrUpdate;
	}

	public boolean create(CcpJsonRepresentation values) {
		boolean created = super.create(values);
		CcpEntityOperationType operation = created ? CcpEntityOperationType.create : CcpEntityOperationType.update;
		this.saveAuditory(values, operation);
		return created;
	}
	
}

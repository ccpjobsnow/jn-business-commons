package com.jn.commons.entities.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.decorators.CcpTimeDecorator;
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
	private final void saveAuditory(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		boolean canNotSaveCopy = this.canSaveCopy() == false;
		if(canNotSaveCopy) {
			return;
		}
		CcpJsonRepresentation audit = this.getAuditRecord(json, operation);
		CcpCrud dependency = CcpDependencyInjection.getDependency(CcpCrud.class);
		dependency.createOrUpdate(JnEntityAudit.INSTANCE, audit);
	}

	public final CcpBulkItem getRecordCopyToBulkOperation(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		
		CcpJsonRepresentation audit = this.getAuditRecord(json, operation);
		CcpBulkItem ccpBulkItem = new CcpBulkItem(audit, CcpEntityOperationType.create, JnEntityAudit.INSTANCE);
		return ccpBulkItem;
	}

	public final String calculateId(CcpJsonRepresentation json) {

		List<String> primaryKeyNames = this.getPrimaryKeyNames();
		
		boolean hasNoPrimaryKey = primaryKeyNames.isEmpty();
		
		if(hasNoPrimaryKey) {
			String string = UUID.randomUUID().toString();
			String hash = new CcpStringDecorator(string).hash().asString("SHA1");
			return hash;
		}
		
		ArrayList<Object> sortedPrimaryKeyValues = this.getSortedPrimaryKeyValues(json);
		
		String replace = sortedPrimaryKeyValues.toString().replace("[", "").replace("]", "");
		String hash = new CcpStringDecorator(replace).hash().asString("SHA1");
		return hash;
	}
	
	private CcpJsonRepresentation getAuditRecord(CcpJsonRepresentation json, CcpEntityOperationType operation) {
		String id = this.getPrimaryKeyValues(json).asUgglyJson();
		String entityName = this.getEntityName();
		CcpJsonRepresentation onlyExistingFields = this.getOnlyExistingFields(json);
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
		boolean delete = super.delete(id);
		CcpJsonRepresentation oneById = this.getOneById(id);
		this.saveAuditory(oneById, CcpEntityOperationType.delete);
		return delete;
	}

	public boolean delete(CcpJsonRepresentation json) {
		boolean delete = super.delete(json);
		this.saveAuditory(json, CcpEntityOperationType.delete);
		return delete;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json) {
		CcpJsonRepresentation createOrUpdate = super.createOrUpdate(json);
		try {
			boolean exists = this.exists(json);
			CcpEntityOperationType operation = exists ? CcpEntityOperationType.create : CcpEntityOperationType.update;
			this.saveAuditory(createOrUpdate, operation);
			return createOrUpdate;
			
		} catch (Exception e) {
			return createOrUpdate;
		}
	}
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation json, String id) {
		CcpJsonRepresentation createOrUpdate = super.createOrUpdate(json, id);
		boolean exists = this.exists(id);
		CcpEntityOperationType operation = exists ? CcpEntityOperationType.create : CcpEntityOperationType.update;
		this.saveAuditory(createOrUpdate, operation);
		return createOrUpdate;
	}

	public boolean create(CcpJsonRepresentation json) {
		boolean created = super.create(json);
		CcpEntityOperationType operation = created ? CcpEntityOperationType.create : CcpEntityOperationType.update;
		this.saveAuditory(json, operation);
		return created;
	}

}

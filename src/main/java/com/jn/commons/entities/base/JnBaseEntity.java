package com.jn.commons.entities.base;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.JnEntityAudit;

public abstract class JnBaseEntity implements CcpEntity{

	final String name = this.getEntityName();
	final CcpEntityField[] fields;
	
	protected JnBaseEntity(CcpEntityField... fields) {
		this.fields = fields;
	}

	@Override
	public CcpEntityField[] getFields() {
		return this.fields;
	}


	public String getEntityName() {
		String simpleName = this.getClass().getSimpleName();
		String snackCase = new CcpStringDecorator(simpleName).text().toSnakeCase().content;
		String substring = snackCase.substring(snackCase.indexOf("entity") + 7);
		return substring;
	}
	
	public String toString() {
		String entityName = this.getEntityName();
		return entityName;
	}
	
	@SuppressWarnings("unchecked")
	public static CcpEntity valueOf(String className, String entityName) {
		Class<?> clazz;
		
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		String packageName = clazz.getPackage().getName();
		String fullNameClass = packageName.replace(".base", "") + "." + entityName;
		try {
			Class<CcpEntity> forName = (Class<CcpEntity>) Class.forName(fullNameClass);
			Constructor<CcpEntity> declaredConstructor = forName.getDeclaredConstructor();
			declaredConstructor.setAccessible(true);
			CcpEntity newInstance = declaredConstructor.newInstance();
			return newInstance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getId(CcpJsonRepresentation values) {

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
	
	public CcpJsonRepresentation getPrimaryKeyValues(CcpJsonRepresentation values) {
		
		List<String> onlyPrimaryKey = this.getPrimaryKeyNames();
		CcpJsonRepresentation jsonPiece = values.getJsonPiece(onlyPrimaryKey);
		return jsonPiece;
	}
	
	private List<String> getPrimaryKeyNames() {
		CcpEntityField[] fields = this.getFields();
		List<String> onlyPrimaryKey = new ArrayList<>(Arrays.asList(fields).stream().filter(x -> x.isPrimaryKey()).map(x -> x.name()).collect(Collectors.toList()));
		return onlyPrimaryKey;
	}
	
	
	protected ArrayList<Object> getSortedPrimaryKeyValues(CcpJsonRepresentation values) {
		
		CcpJsonRepresentation primaryKeyValues = this.getPrimaryKeyValues(values);
		
		List<String> primaryKeyNames = this.getPrimaryKeyNames();
		
		Set<String> missingKeys = primaryKeyValues.getMissingKeys(primaryKeyNames);

		boolean isMissingKeys = missingKeys.isEmpty() == false;
		
		if(isMissingKeys) {
			throw new RuntimeException("It is missing the keys '" + missingKeys + "' from entity '" + this + "' in the object " + values );
		}
		
		TreeMap<String, Object> treeMap = new TreeMap<>(primaryKeyValues.content);
		Collection<Object> values2 = treeMap.values();
		ArrayList<Object> onlyPrimaryKeys = new ArrayList<>(values2);
		return onlyPrimaryKeys;
	}
	
	protected void saveAuditory(CcpJsonRepresentation values, CcpEntityOperationType operation) {

		CcpJsonRepresentation audit = this.getAuditRecord(values, operation);
		CcpCrud dependency = CcpDependencyInjection.getDependency(CcpCrud.class);
		dependency.createOrUpdate(JnEntityAudit.INSTANCE, audit);
	}
	
	public CcpBulkItem getRecordToBulkOperation(CcpJsonRepresentation values, CcpEntityOperationType operation) {
		
		CcpJsonRepresentation audit = this.getAuditRecord(values, operation);
		CcpBulkItem ccpBulkItem = new CcpBulkItem(audit, CcpEntityOperationType.create, JnEntityAudit.INSTANCE);
		return ccpBulkItem;
	}

	private CcpJsonRepresentation getAuditRecord(CcpJsonRepresentation values, CcpEntityOperationType operation) {
		String id = this.getId(values);
		String entityName = this.getEntityName();
	
		CcpJsonRepresentation audit = 
				CcpConstants.EMPTY_JSON
				.put("timestamp", System.currentTimeMillis())
				.put("operation", operation)
				.put("entity", entityName)
				.put("json", values)
				.put("id", id)
		;
		return audit;
	}
	
	public boolean create(CcpJsonRepresentation values) {
		boolean created = CcpEntity.super.create(values);
		CcpEntityOperationType operation = created ? CcpEntityOperationType.create : CcpEntityOperationType.update;
		CcpJsonRepresentation auditRecord = this.getAuditRecord(values, operation);
		JnEntityAudit.INSTANCE.createOrUpdate(auditRecord);
		return created;
	}

	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation data, String id) {
		boolean exists = this.exists(id);
		CcpEntityOperationType operation = exists ? CcpEntityOperationType.create : CcpEntityOperationType.update;
		CcpJsonRepresentation createOrUpdate = CcpEntity.super.createOrUpdate(data, id);
		CcpJsonRepresentation auditRecord = this.getAuditRecord(data, operation);
		JnEntityAudit.INSTANCE.createOrUpdate(auditRecord);
		return createOrUpdate;
	}
	
	public CcpJsonRepresentation createOrUpdate(CcpJsonRepresentation values) {
		CcpJsonRepresentation createOrUpdate = CcpEntity.super.createOrUpdate(values);
		boolean exists = this.exists(values);
		CcpEntityOperationType operation = exists ? CcpEntityOperationType.create : CcpEntityOperationType.update;
		CcpJsonRepresentation auditRecord = this.getAuditRecord(values, operation);
		JnEntityAudit.INSTANCE.createOrUpdate(auditRecord);
		return createOrUpdate;
	}
	
	public boolean delete(CcpJsonRepresentation values) {
		boolean delete = CcpEntity.super.delete(values);
		CcpJsonRepresentation auditRecord = this.getAuditRecord(values, CcpEntityOperationType.delete);
		JnEntityAudit.INSTANCE.createOrUpdate(auditRecord);
		return delete;
	}
	
	public boolean delete(String id) {
		boolean delete = CcpEntity.super.delete(id);
		CcpJsonRepresentation oneById = this.getOneById(id);
		CcpJsonRepresentation auditRecord = this.getAuditRecord(oneById, CcpEntityOperationType.delete);
		JnEntityAudit.INSTANCE.createOrUpdate(auditRecord);
		return delete;
	}
}

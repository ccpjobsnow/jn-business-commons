package com.jn.commons.entities.base;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.dao.CcpDao;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpEntityOperationType;
import com.ccp.especifications.db.utils.CcpTimeOption;
import com.ccp.process.CcpProcessStatus;
import com.ccp.process.CcpSuccessStatus;
import com.jn.commons.business.JnCommonsBusinessDeleteEntity;
import com.jn.commons.business.JnCommonsBusinessSaveEntity;
import com.jn.commons.entities.JnEntityAudit;

public abstract class JnBaseEntity implements CcpEntity{

	final CcpTimeOption timeOption;
	final CcpEntityField[] fields;
	final boolean auditable;
	
	protected JnBaseEntity(CcpTimeOption timeOption, CcpEntityField... fields) {
		this.auditable = CcpTimeOption.none.equals(timeOption);
		this.timeOption = timeOption;
		this.fields = fields;
	}

	protected JnBaseEntity(CcpEntityField[] fields) {
		this.timeOption = CcpTimeOption.none;
		this.auditable = true;
		this.fields = fields;
	}

	protected JnBaseEntity(boolean auditable, CcpTimeOption timeOption,  CcpEntityField... fields) {
		this.timeOption = timeOption;
		this.auditable = auditable;
		this.fields = fields;
	}

	public CcpEntityField[] getFields() {
		return this.fields;
	}

	public void saveAuditory(CcpJsonRepresentation values, CcpEntityOperationType operation) {

		if(this.auditable == false) {
			return;
		}

		String id = this.getId(values);
		CcpJsonRepresentation audit = CcpConstants.EMPTY_JSON
		.put("id", id)
		.put("json", values)
		.put("entity", this.name())
		.put("operation", operation)

		.put("date", System.currentTimeMillis());
	
		CcpDependencyInjection.getDependency(CcpDao.class).createOrUpdate(new JnEntityAudit(), audit);
	}

	public boolean exceededTries(CcpJsonRepresentation values, String fieldName, int limit) {
		
		for(int k = 1; k <= limit; k++) {
			
			CcpJsonRepresentation put = values.put(fieldName, k);
			
			boolean exists = this.exists(put);
			
			if(exists == false) {
				this.createOrUpdate(put);
				return false;
			}
		}
		return true;
	}
	
	public JnCommonsBusinessSaveEntity getSaver() {
		return this.getSaver(new CcpSuccessStatus());
	}
	public JnCommonsBusinessSaveEntity getSaver(CcpProcessStatus statusToReturnAfterSaving) {
		return new JnCommonsBusinessSaveEntity(this, statusToReturnAfterSaving);
	}

	public JnCommonsBusinessDeleteEntity getDeleter() {
		return this.getDeleter(new CcpSuccessStatus());
	}
	
	public JnCommonsBusinessDeleteEntity getDeleter(CcpProcessStatus statusToReturnAfterSaving) {
		return new JnCommonsBusinessDeleteEntity(this, statusToReturnAfterSaving);
	}
	
	public CcpJsonRepresentation getOnlyExistingFields(CcpJsonRepresentation values) {
		CcpEntityField[] fields = this.getFields();
		String[] array = Arrays.asList(fields).stream().map(x -> x.name()).collect(Collectors.toList()).toArray(new String[fields.length]);
		CcpJsonRepresentation subMap = values.getJsonPiece(array);
		return subMap;
	}
//
	@Override
	public boolean isAuditable() {
		return this.auditable;
	}

	@Override
	public String name() {
		String simpleName = this.getClass().getSimpleName();
		String snackCase = new CcpStringDecorator(simpleName).text().toSnakeCase();
		String substring = snackCase.substring(snackCase.indexOf("entity") + 7);
		return substring;
	}
	
	@Override
	public String toString() {
		return this.name();
	}
	
	@SuppressWarnings("unchecked")
	public static CcpEntity valueOf(Class<? extends JnBaseEntity> clazz, String entityName) {
		String packageName = clazz.getPackage().getName();
		String fullNameClass = packageName + "." + entityName;
		try {
			Class<CcpEntity> forName = (Class<CcpEntity>) Class.forName(fullNameClass);
			CcpEntity newInstance = forName.getDeclaredConstructor().newInstance();
			return newInstance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static CcpEntity valueOf(String name) {
		return valueOf(JnBaseEntity.class, name);
	}

	@Override
	public CcpTimeOption getTimeOption() {
		return this.timeOption;
	}
}

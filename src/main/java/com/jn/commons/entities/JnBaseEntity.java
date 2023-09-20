package com.jn.commons.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.dao.CcpDao;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpEntityOperationType;
import com.ccp.exceptions.db.CcpEntityMissingKeys;
import com.ccp.process.CcpProcessStatus;
import com.ccp.process.CcpSuccessStatus;
import com.jn.commons.business.JnCommonsBusinessDeleteEntity;
import com.jn.commons.business.JnCommonsBusinessSaveEntity;
import com.jn.commons.utils.JnTimeOption;

public abstract class JnBaseEntity implements CcpEntity{

	final JnTimeOption timeOption;
	final CcpEntityField[] fields;
	final boolean auditable;
	
	protected JnBaseEntity(JnTimeOption timeOption, CcpEntityField... fields) {
		this.auditable = JnTimeOption.none.equals(timeOption);
		this.timeOption = timeOption;
		this.fields = fields;
	}

	protected JnBaseEntity(CcpEntityField[] fields) {
		this.timeOption = JnTimeOption.none;
		this.auditable = true;
		this.fields = fields;
	}

	protected JnBaseEntity(boolean auditable, JnTimeOption timeOption,  CcpEntityField... fields) {
		this.timeOption = timeOption;
		this.auditable = auditable;
		this.fields = fields;
	}

	public CcpEntityField[] getFields() {
		return this.fields;
	}

	public String getId(CcpMapDecorator values) {
		Long time = System.currentTimeMillis();
		String formattedCurrentDate = this.timeOption.getFormattedCurrentDate(time);
		
		if(fields.length == 0) {
			
			if(JnTimeOption.none != this.timeOption) {
				return formattedCurrentDate;
			}
			
			return UUID.randomUUID().toString();
		}
		
		List<CcpEntityField> missingKeys = Arrays.asList(fields).stream().filter(key -> this.isEmptyPrimaryKey(key, values))
				.collect(Collectors.toList());
		
		boolean isMissingKeys = missingKeys.isEmpty() == false;
		
		if(isMissingKeys) {
			throw new CcpEntityMissingKeys(this, missingKeys, values);
		}
		
		
		List<String> onlyPrimaryKeys = Arrays.asList(fields).stream().filter(key -> key.isPrimaryKey()).map(key -> this.getPrimaryKeyFieldValue(key, values)).collect(Collectors.toList());

		if(onlyPrimaryKeys.isEmpty()) {
			String hash = new CcpStringDecorator(formattedCurrentDate).hash().asString("SHA1");
			return hash;
		}
		
		Collections.sort(onlyPrimaryKeys);
		onlyPrimaryKeys = new ArrayList<>(onlyPrimaryKeys);

		if(formattedCurrentDate.trim().isEmpty() == false) {
			onlyPrimaryKeys.add(0, formattedCurrentDate);
		}
		
		String replace = onlyPrimaryKeys.toString().replace("[", "").replace("]", "");
		String hash = new CcpStringDecorator(replace).hash().asString("SHA1");
		return hash;
	}

	public void saveAuditory(CcpMapDecorator values, CcpEntityOperationType operation) {

		if(this.auditable == false) {
			return;
		}

		String id = this.getId(values);
		CcpMapDecorator audit = new CcpMapDecorator()
		.put("id", id)
		.put("json", values)
		.put("entity", this.name())
		.put("operation", operation)

		.put("date", System.currentTimeMillis());
	
		CcpDependencyInjection.getDependency(CcpDao.class).createOrUpdate(new JnEntityAudit(), audit);
	}

	public boolean exceededTries(CcpMapDecorator values, String fieldName, int limit) {
		
		for(int k = 1; k <= limit; k++) {
			
			CcpMapDecorator put = values.put(fieldName, k);
			
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
	
	public CcpMapDecorator getOnlyExistingFields(CcpMapDecorator values) {
		CcpEntityField[] fields = this.getFields();
		String[] array = Arrays.asList(fields).stream().map(x -> x.name()).collect(Collectors.toList()).toArray(new String[fields.length]);
		CcpMapDecorator subMap = values.getSubMap(array);
		return subMap;
	}
	private boolean isEmptyPrimaryKey(CcpEntityField key, CcpMapDecorator values) {
		
		if(key.isPrimaryKey() == false) {
			return false;
		}
		
		String primaryKeyFieldValue = this.getPrimaryKeyFieldValue(key, values);
		if("_".equals(primaryKeyFieldValue)) {
			return true;
		}
		
		return false;
	}
	
	private String getPrimaryKeyFieldValue(CcpEntityField key, CcpMapDecorator values) {
		
		boolean notCollection = values.get(key.name()) instanceof Collection<?> == false;
		
		if(notCollection) {
			String primaryKeyFieldValue = values.getAsString(key.name()).trim() + "_";
			return primaryKeyFieldValue;
		}
		
		Collection<?> col = values.getAsObject(key.name());
		ArrayList<?> list = new ArrayList<>(col);
		list.sort((a, b) -> ("" + a).compareTo("" + b));
		String primaryKeyFieldValue = list.stream().map(x -> x.toString().trim()).collect(Collectors.toList()).toString() + "_";
		return primaryKeyFieldValue;
	}
	

	@Override
	public boolean isAuditable() {
		return this.auditable;
	}

	@Override
	public String name() {
		return null;
	}
	

	
	@SuppressWarnings("unchecked")
	public static CcpEntity valueOf(Class<? extends JnBaseEntity> clazz, String entityName) {
		String packageName = clazz.getPackage().getName();
		String fullNameClass = packageName + "." + entityName;
		try {
			Class<CcpEntity> forName = (Class<CcpEntity>) Class.forName(fullNameClass);
			CcpEntity newInstance = forName.newInstance();
			return newInstance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static CcpEntity valueOf(String name) {
		return valueOf(JnBaseEntity.class, name);
	}
}

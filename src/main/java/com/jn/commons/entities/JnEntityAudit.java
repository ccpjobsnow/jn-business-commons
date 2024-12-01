package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.ccp.validation.annotations.ValidationRules;
import com.jn.commons.validations.JsonFieldsValidationJnAudit;
//audit
@ValidationRules(rulesClass = JsonFieldsValidationJnAudit.class)
public class JnEntityAudit {

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityAudit.class);

	
	public static enum Fields implements CcpEntityField{
		timestamp(true), 
		operation(false),
		date(false),
		entity(true), 
		id(true),
		json(false)
		;
		
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}
	}
	
	public boolean isCopyableEntity() {
		return false;
	}
	
}

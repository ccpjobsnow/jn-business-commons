package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.validation.annotations.ValidationRules;
import com.jn.commons.entities.base.JnAuditableEntity;
import com.jn.commons.validations.JsonFieldsValidationJnAudit;

@ValidationRules(rulesClass = JsonFieldsValidationJnAudit.class)
public class JnEntityAudit extends JnAuditableEntity{

	public static final JnEntityAudit INSTANCE = new JnEntityAudit();

	private JnEntityAudit() {
		super(Fields.values());
	}
	
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
	
	public boolean canSaveCopy() {
		return false;
	}
	
}

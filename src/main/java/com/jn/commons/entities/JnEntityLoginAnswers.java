package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.validation.annotations.ValidationRules;
import com.jn.commons.entities.base.JnAuditableEntity;
import com.jn.commons.validations.JsonFieldsValidationJnLoginAnswers;

@ValidationRules(rulesClass = JsonFieldsValidationJnLoginAnswers.class)
public class JnEntityLoginAnswers extends JnAuditableEntity{

	public static final JnEntityLoginAnswers INSTANCE = new JnEntityLoginAnswers();
	
	private JnEntityLoginAnswers() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), channel(false), goal(false)
		;
		
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}

	}

}

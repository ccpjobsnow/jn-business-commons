package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.ccp.validation.annotations.ValidationRules;
import com.jn.commons.validations.JsonFieldsValidationJnLoginAnswers;

@ValidationRules(rulesClass = JsonFieldsValidationJnLoginAnswers.class)
//super(Fields.values());
public class JnEntityLoginAnswers {

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityLoginAnswers.class);

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

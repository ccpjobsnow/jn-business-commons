package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityVersionable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.ccp.validation.annotations.CcpJsonValidation;
import com.jn.commons.utils.JnEntityVersionable;
import com.jn.commons.validations.JsonFieldsValidationJnLoginAnswers;

@CcpEntityVersionable(versionableEntityFactory = JnEntityVersionable.class)
@CcpJsonValidation(rulesClass = JsonFieldsValidationJnLoginAnswers.class)
@CcpEntitySpecifications(cacheableEntity = true)
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

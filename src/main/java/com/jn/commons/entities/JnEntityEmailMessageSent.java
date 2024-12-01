package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
//disposable daily
public class JnEntityEmailMessageSent {
	
	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityEmailMessageSent.class);

	public static enum Fields implements CcpEntityField{
		subject(false), subjectType(true), email(true), sender(false), message(false)
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

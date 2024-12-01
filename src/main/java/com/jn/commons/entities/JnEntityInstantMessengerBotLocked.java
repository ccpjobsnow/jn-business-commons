package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
//entity
public class JnEntityInstantMessengerBotLocked {
	
	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityInstantMessengerBotLocked.class);

	public static enum Fields implements CcpEntityField{
		token(true), recipient(true), subjectType(false), message(false)
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

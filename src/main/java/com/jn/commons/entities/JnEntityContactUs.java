package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
//disposable DAILY
public class JnEntityContactUs{

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityContactUs.class);
	
	public static enum Fields implements CcpEntityField{
		subjectType(true), email(true), subject(false), message(false), chatId(false), sender(false)
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

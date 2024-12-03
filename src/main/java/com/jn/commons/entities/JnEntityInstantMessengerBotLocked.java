package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityFactory;


@CcpEntitySpecifications(cacheableEntity = true)
public class JnEntityInstantMessengerBotLocked {
	
	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityInstantMessengerBotLocked.class).entityInstance;

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

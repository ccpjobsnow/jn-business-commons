package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;

//super("login_token_locked", CcpLongevityEntity.daily, Fields.values());
public class JnEntityLoginToken {
	
	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityLoginToken.class);

	public static enum Fields implements CcpEntityField{
		email(true), originalEmail(false), tokenHash(false),ip(true), coordinates(false), macAddress(false), userAgent(true)
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

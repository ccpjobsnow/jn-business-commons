package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;

//super(CcpLongevityEntity.hourly, Fields.values());
public class JnEntityLoginSessionToken {

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityLoginSessionToken.class);
	
	public static enum Fields implements CcpEntityField{
		email(true), sessionToken(true), ip(true), coordinates(false), macAddress(false), userAgent(true)
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

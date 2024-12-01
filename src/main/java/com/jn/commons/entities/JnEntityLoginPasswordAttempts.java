package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;

//super(CcpLongevityEntity.daily, Fields.values());
public class JnEntityLoginPasswordAttempts {

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityLoginPasswordAttempts.class);
	
	private JnEntityLoginPasswordAttempts() {
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), attempts(false)
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

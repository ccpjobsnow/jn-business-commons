package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
//disposable entity
public class JnEntityJobsnowError{

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityJobsnowError.class);
	
	public static enum Fields implements CcpEntityField{
		cause(false), stackTrace(false), type(true), message(false)
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

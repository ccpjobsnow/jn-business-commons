package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;

//super(Fields.values());
public class JnEntityRecordToReprocess {
	
	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityRecordToReprocess.class);

	public static enum Fields implements CcpEntityField{
		timestamp(true), operation(false),
		entity(true), id(true),
		json(false), status(false),
		reason(false), errorType(false)
		;
		
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}
	}
	
	public boolean isCopyableEntity() {
		return false;
	}
}

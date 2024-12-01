package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
//audit
public class JnEntityAsyncTask{

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityAsyncTask.class);

	public static enum Fields implements CcpEntityField{
		started(false), finished(false), enlapsedTime(false), data(false),
		topic(false), request(false), messageId(true), success(false), 
		response(false)
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

package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityFactory;

@CcpEntitySpecifications(cacheableEntity = false)
public class JnEntityAsyncTask{

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityAsyncTask.class).entityInstance;

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
}

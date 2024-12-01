package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
//audit
public class JnEntityEmailReportedAsSpam {

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityEmailReportedAsSpam.class);
	
	public static enum Fields implements CcpEntityField{
		subject(false), subjectType(false), email(true), sender(true), message(false)
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

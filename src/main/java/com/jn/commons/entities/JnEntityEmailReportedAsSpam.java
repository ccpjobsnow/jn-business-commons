package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityEmailReportedAsSpam extends JnBaseEntity{
	public JnEntityEmailReportedAsSpam() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		subject(false), subjectType(false), email(true), sender(true), emailMessage(false)
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

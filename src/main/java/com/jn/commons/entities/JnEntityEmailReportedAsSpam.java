package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnAuditableEntity;

public class JnEntityEmailReportedAsSpam extends JnAuditableEntity{

	public static final JnEntityEmailReportedAsSpam INSTANCE = new JnEntityEmailReportedAsSpam();
	
	private JnEntityEmailReportedAsSpam() {
		super(Fields.values());
	}
	
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

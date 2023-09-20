package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityEmailParametersToSend extends JnBaseEntity{
	public JnEntityEmailParametersToSend() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		email(false), sender(false), templateId(true), subjectType(false), moreParameters(false)
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

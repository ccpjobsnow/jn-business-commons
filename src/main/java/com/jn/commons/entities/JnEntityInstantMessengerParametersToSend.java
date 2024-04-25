package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityInstantMessengerParametersToSend extends JnBaseEntity{
	
	public static final JnEntityInstantMessengerParametersToSend INSTANCE = new JnEntityInstantMessengerParametersToSend();

	private JnEntityInstantMessengerParametersToSend() {
		super(Fields.values());
	}
	
	
	public static enum Fields implements CcpEntityField{
		recipient(false), templateId(true), subjectType(false), moreParameters(false)
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

package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityInstantMessengerTemplateMessage extends JnBaseEntity{
	public JnEntityInstantMessengerTemplateMessage() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		templateId(true),language(true), subject(false), message(false)
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

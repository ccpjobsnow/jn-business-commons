package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityInstantMessengerBotLocked extends JnBaseEntity{
	public JnEntityInstantMessengerBotLocked() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		token(true), recipient(true), subjectType(false), message(false)
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

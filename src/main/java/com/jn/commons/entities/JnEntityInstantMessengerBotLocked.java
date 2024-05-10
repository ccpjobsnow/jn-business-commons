package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnAuditableEntity;

public class JnEntityInstantMessengerBotLocked extends JnAuditableEntity{
	
	public static final JnEntityInstantMessengerBotLocked INSTANCE = new JnEntityInstantMessengerBotLocked();

	private JnEntityInstantMessengerBotLocked() {
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

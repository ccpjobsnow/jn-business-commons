package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityEmailMessageSent extends JnBaseEntity{
	
	public static final JnEntityEmailMessageSent INSTANCE = new JnEntityEmailMessageSent();

	
	private JnEntityEmailMessageSent() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		subject(false), subjectType(true), email(true), sender(false), emailMessage(false)
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

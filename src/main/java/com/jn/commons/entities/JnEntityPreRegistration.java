package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityPreRegistration extends JnBaseEntity{
	public JnEntityPreRegistration() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		email(true), channel(false), goal(false)
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

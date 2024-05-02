package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityPasswordAttempts extends JnBaseEntity{

	public static final JnEntityPasswordAttempts INSTANCE = new JnEntityPasswordAttempts();
	
	private JnEntityPasswordAttempts() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), attempts(false)
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

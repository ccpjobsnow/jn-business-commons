package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityUnlockTokenTries extends JnBaseEntity{
	
	public static final JnEntityUnlockTokenTries INSTANCE = new JnEntityUnlockTokenTries();
	
	private JnEntityUnlockTokenTries() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		email(true), tries(true)
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

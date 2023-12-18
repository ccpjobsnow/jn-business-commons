package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;

public class JnEntityRequestUnlockToken extends JnBaseEntity{
	public JnEntityRequestUnlockToken() {
		super(CcpTimeOption.ddMMyyyy, Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		email(true), language(false)
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

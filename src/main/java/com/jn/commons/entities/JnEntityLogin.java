package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;

public class JnEntityLogin extends JnBaseEntity{
	public JnEntityLogin() {
		super(CcpTimeOption.ddMMyyyy, Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		email(true), token(false), ip(false), coordinates(false), macAddress(false), userAgent(false)
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

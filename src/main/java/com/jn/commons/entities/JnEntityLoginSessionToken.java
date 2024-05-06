package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityLoginSessionToken extends JnBaseEntity{

	public static final JnEntityLoginSessionToken INSTANCE = new JnEntityLoginSessionToken();
	
	private JnEntityLoginSessionToken() {
		super(CcpTimeOption.ddMMyyyy, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), sessionToken(true), ip(true), coordinates(false), macAddress(false), userAgent(true)
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

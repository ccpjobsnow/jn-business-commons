package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityLoginSessionCurrent extends JnBaseEntity{

	public static final JnEntityLoginSessionCurrent INSTANCE = new JnEntityLoginSessionCurrent();
	
	private JnEntityLoginSessionCurrent() {
		super(CcpTimeOption.ddMMyyyy, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), sessionToken(false), ip(false), coordinates(false), macAddress(false), userAgent(false)
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

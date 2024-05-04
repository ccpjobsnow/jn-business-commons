package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityLoginSession extends JnBaseEntity{

	public static final JnEntityLoginSession INSTANCE = new JnEntityLoginSession();
	
	private JnEntityLoginSession() {
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

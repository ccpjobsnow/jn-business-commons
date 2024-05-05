package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityLoginToken extends JnBaseEntity{
	
	public static final JnEntityLoginToken INSTANCE = new JnEntityLoginToken();

	private JnEntityLoginToken() {
		super(CcpTimeOption.ddMMyyyy, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), tokenHash(false)
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

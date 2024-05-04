package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityLoginPassword extends JnBaseEntity{
	
	public static final JnEntityLoginPassword INSTANCE = new JnEntityLoginPassword();
	
	private JnEntityLoginPassword() {
		super(CcpTimeOption.ddMM, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), password(false)
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

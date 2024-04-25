package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityRequestUnlockTokenAnswered extends JnBaseEntity{

	public static final JnEntityRequestUnlockTokenAnswered INSTANCE = new JnEntityRequestUnlockTokenAnswered();

	
	private JnEntityRequestUnlockTokenAnswered() {
		super(CcpTimeOption.ddMMyyyy, Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		email(true), chatId(false), password(false), token (false)
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

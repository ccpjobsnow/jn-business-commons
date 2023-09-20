package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.utils.JnTimeOption;

public class JnEntityRequestTokenAgainAnswered extends JnBaseEntity{
	public JnEntityRequestTokenAgainAnswered() {
		super(JnTimeOption.ddMMyyyy, Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		email(true),
		token(false),
		chatId(false)
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

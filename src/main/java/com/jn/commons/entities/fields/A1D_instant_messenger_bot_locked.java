package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_instant_messenger_bot_locked implements CcpField{
		botToken(true), chatId(true), subjectType(false), telegramMessage(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_instant_messenger_bot_locked(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

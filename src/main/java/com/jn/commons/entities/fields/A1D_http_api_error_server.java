package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_instant_messenger_api_unavailable implements CcpField{
		botToken(false), chatId(false), subjectType(false), message(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_instant_messenger_api_unavailable(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_request_unlock_token_responsible implements CcpEntityField{
		chatId(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_request_unlock_token_responsible(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

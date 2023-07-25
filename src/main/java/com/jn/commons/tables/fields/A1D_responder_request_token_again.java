package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A1D_responder_request_token_again implements CcpDbTableField{
		chatId(true)
	;
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_responder_request_token_again(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

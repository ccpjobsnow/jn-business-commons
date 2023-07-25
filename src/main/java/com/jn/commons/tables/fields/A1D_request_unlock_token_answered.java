package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A1D_request_unlock_token_answered implements CcpDbTableField{
		email(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_request_unlock_token_answered(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

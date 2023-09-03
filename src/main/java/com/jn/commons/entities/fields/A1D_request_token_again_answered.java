package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_request_token_again_answered implements CcpField{
		email(true),
		token(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_request_token_again_answered(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

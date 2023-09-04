package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_request_token_again implements CcpField{
		email(true), language(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_request_token_again(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_unlock_token_tries implements CcpField{
		email(true), tries(true)
	;
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_unlock_token_tries(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

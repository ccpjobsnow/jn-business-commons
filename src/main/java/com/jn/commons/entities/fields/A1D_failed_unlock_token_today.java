package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_failed_unlock_token_today implements CcpField{
		email(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_failed_unlock_token_today(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}
package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_login implements CcpField{
		email(true), token(false), ip(false), coordinates(false), macAddress(false), userAgent(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_login(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

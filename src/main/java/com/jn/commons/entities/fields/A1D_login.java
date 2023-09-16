package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_login implements CcpEntityField{
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

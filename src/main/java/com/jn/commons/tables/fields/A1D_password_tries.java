package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_password_tries implements CcpField{
		email(true), tries(true)
	;

	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_password_tries(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	

}


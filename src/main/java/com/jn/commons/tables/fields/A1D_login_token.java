package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A1D_login_token implements CcpDbTableField{
		email(true), token(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_login_token(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A1D_login_conflict implements CcpDbTableField{
		email(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_login_conflict(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

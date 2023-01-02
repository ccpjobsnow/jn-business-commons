package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A1D_password_tries implements CcpDbTableField{
		audit(false), email(true), tries(true)
	;

	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_password_tries(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	

}


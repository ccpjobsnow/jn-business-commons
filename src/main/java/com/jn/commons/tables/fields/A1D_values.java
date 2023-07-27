package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_values implements CcpField{
		id(true), value(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_values(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

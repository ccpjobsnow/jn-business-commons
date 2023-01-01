package com.jn.commons.tables.fields;

import com.ccp.especifications.db.table.CcpDbTableField;

public enum A1D_static implements CcpDbTableField{
		audit(false), name(true), value(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_static(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

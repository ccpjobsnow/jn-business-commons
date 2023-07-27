package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A1D_message implements CcpDbTableField{
		id(true), language(true), value(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_message(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}
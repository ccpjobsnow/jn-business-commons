package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_message implements CcpField{
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

package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_audit implements CcpField{
		date(true), operation(false),
		entity(true), id(true),
		json(false)
	;

	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_audit(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	

}


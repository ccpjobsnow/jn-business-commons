package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_pre_registration implements CcpEntityField{
		email(true), channel(false), goal(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_pre_registration(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

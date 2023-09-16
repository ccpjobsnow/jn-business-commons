package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_login_conflict implements CcpEntityField{
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

package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_login_token implements CcpEntityField{
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

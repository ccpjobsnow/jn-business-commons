package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_password_tries implements CcpEntityField{
		email(true), tries(true)
	;

	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_password_tries(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	

}


package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_failed_unlock_token implements CcpEntityField{
		email(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_failed_unlock_token(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_unlocked_password implements CcpEntityField{
		email(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_unlocked_password(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

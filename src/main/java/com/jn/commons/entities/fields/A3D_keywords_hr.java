package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A3D_keywords_hr implements CcpField{
		keyword(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_keywords_hr(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

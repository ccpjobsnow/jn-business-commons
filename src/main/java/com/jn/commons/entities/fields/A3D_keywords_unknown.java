package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A3D_keywords_unknown implements CcpField{
		keyword(true), jobType(true), keywordType(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_keywords_unknown(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

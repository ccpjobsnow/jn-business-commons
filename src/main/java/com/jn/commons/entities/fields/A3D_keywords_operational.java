package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A3D_keywords_operational implements CcpEntityField{
		keyword(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_keywords_operational(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

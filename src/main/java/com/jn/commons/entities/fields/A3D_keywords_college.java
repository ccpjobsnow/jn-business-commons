package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A3D_keywords_college implements CcpEntityField{
		keyword(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_keywords_college(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
	
}

package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A3D_keywords_it implements CcpEntityField{
		keyword(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_keywords_it(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	

}

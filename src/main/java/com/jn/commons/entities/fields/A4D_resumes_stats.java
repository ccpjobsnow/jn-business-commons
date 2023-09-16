package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A4D_resumes_stats implements CcpEntityField{
	searchId(true), results(false), 
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A4D_resumes_stats(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

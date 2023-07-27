package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A4D_resumes_list implements CcpField{
		searchId(true), results(false), 
		total(false), from(false), size(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A4D_resumes_list(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

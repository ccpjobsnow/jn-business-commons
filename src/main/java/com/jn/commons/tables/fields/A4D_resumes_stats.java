package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A4D_resumes_stats implements CcpDbTableField{
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

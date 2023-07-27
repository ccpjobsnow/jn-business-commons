package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A4D_search_resumes_list implements CcpField{
	requiredKeywords(true),
	optionalKeywords(true),
	recruiter(true),
	seniority(true),
	ddd(true),
	pcd(false),
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A4D_search_resumes_list(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

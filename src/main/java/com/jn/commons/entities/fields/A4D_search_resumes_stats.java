package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A4D_search_resumes_stats implements CcpEntityField{
		requiredKeywords(true),
		optionalKeywords(true),
		recruiter(true),
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A4D_search_resumes_stats(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A4D_search_resumes_stats implements CcpDbTableField{
		audit(false), recruiter(false), optionalKeywords(false), requiredKeywords(false), total(false), ddd(false),
		results(false), seniority(false), jobType(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A4D_search_resumes_stats(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

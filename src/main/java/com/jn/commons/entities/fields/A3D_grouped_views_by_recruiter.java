package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A3D_grouped_views_by_recruiter implements CcpEntityField{
		recruiter(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_grouped_views_by_recruiter(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

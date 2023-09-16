package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A3D_candidate_view_resume implements CcpEntityField{
		email(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_candidate_view_resume(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

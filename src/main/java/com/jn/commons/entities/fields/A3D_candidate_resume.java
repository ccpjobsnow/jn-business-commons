package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A3D_candidate_resume implements CcpEntityField{
		resume(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_candidate_resume(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

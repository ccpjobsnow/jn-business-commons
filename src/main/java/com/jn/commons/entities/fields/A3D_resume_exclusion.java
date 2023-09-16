package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A3D_resume_exclusion implements CcpEntityField{
		resume(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_resume_exclusion(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A3D_recruiter_view_resume implements CcpField{
		recruiter(true), resume(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_recruiter_view_resume(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

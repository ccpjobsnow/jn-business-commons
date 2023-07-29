package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A3D_resume_exclusion implements CcpField{
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

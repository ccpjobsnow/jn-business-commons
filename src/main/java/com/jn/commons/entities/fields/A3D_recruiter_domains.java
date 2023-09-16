package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A3D_recruiter_domains implements CcpEntityField{
		prefix(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_recruiter_domains(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

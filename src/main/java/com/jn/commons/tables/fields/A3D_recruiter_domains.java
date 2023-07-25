package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A3D_recruiter_domains implements CcpDbTableField{
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

package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A3D_resume_exclusion implements CcpDbTableField{
		audit(false), resume(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_resume_exclusion(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A3D_candidate_resume implements CcpDbTableField{
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

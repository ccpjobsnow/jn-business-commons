package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A3D_candidate_view_resume implements CcpDbTableField{
		audit(false), email(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_candidate_view_resume(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

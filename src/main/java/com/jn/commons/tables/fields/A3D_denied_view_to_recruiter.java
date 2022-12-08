package com.jn.commons.tables.fields;

import com.ccp.especifications.db.table.CcpDbTableField;

public enum A3D_denied_view_to_recruiter implements CcpDbTableField{
		audit(false), recruiter(true), resume(true), domain(false)
	;
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_denied_view_to_recruiter(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A3D_grouped_views_by_recruiter implements CcpDbTableField{
		recruiter(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_grouped_views_by_recruiter(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

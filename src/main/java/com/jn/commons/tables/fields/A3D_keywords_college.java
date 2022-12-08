package com.jn.commons.tables.fields;

import com.ccp.especifications.db.table.CcpDbTableField;

public enum A3D_keywords_college implements CcpDbTableField{
		audit(false), keyword(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_keywords_college(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
	
}

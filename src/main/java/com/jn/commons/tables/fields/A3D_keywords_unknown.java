package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A3D_keywords_unknown implements CcpDbTableField{
		audit(false), keyword(true), jobType(true), keywordType(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_keywords_unknown(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

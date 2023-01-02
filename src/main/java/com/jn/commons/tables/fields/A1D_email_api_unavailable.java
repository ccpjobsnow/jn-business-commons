package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A1D_email_api_unavailable implements CcpDbTableField{
		audit(false), subject(false), subjectType(false), emails(false), sender(false), message(false)
	;
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_api_unavailable(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

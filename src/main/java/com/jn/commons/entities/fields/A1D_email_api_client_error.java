package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_email_api_client_error implements CcpField{
		subject(false), subjectType(false), emails(false), sender(false), message(false), details(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_api_client_error(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
}

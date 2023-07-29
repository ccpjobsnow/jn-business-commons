package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_email_reported_as_spam implements CcpField{
		subject(false), subjectType(false), email(true), sender(true), message(false)
	;
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_reported_as_spam(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

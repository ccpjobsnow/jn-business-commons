package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_email_reported_as_spam implements CcpEntityField{
		subject(false), subjectType(false), email(true), sender(true), emailMessage(false)
	;
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_reported_as_spam(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

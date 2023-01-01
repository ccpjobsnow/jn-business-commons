package com.jn.commons.tables.fields;

import com.ccp.especifications.db.table.CcpDbTableField;

public enum A1D_email_reported_as_spam implements CcpDbTableField{
	audit(false), subject(false), subjectType(false), email(true), sender(true), message(false)
	;
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_reported_as_spam(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

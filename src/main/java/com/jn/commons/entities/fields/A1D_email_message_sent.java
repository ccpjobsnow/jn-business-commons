package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_email_message_sent implements CcpField{
	subject(false), subjectType(true), email(true), sender(false), emailMessage(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_message_sent(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
}

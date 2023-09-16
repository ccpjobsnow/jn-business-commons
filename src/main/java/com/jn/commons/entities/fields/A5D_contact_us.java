package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A5D_contact_us implements CcpEntityField{
		subjectType(true), email(true), subject(false), message(false), chatId(false), sender(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A5D_contact_us(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.tables.fields;

import com.ccp.especifications.db.table.CcpDbTableField;

public enum A5D_contact_us implements CcpDbTableField{
		audit(false), subjectType(true), email(false), subject(false), message(false), chatId(false), emailFrom(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A5D_contact_us(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

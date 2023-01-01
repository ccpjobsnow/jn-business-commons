package com.jn.commons.tables.fields;

import com.ccp.especifications.db.table.CcpDbTableField;

public enum A1D_email_try_to_send_message implements CcpDbTableField{
	audit(false), subject(false), subjectType(false), emails(false), sender(true), message(false), tries(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_try_to_send_message(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

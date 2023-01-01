package com.jn.commons.tables.fields;

import com.ccp.especifications.db.table.CcpDbTableField;

public enum A1D_instant_messenger_try_to_send_message implements CcpDbTableField{
		audit(false), botToken(true), chatId(true), subjectType(false), message(false), tries(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_instant_messenger_try_to_send_message(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

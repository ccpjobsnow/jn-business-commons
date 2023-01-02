package com.jn.commons.tables.fields;

import com.ccp.especifications.db.utils.CcpDbTableField;

public enum A1D_instant_messenger_try_to_send_message implements CcpDbTableField{
		audit(false), botToken(true), chatId(false), subjectType(false), message(false), tries(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_instant_messenger_try_to_send_message(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

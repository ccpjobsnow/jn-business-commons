package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_instant_messenger_message_sent implements CcpField{
		token(true), recipient(true), subjectType(false), message(false), interval(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_instant_messenger_message_sent(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_email_try_to_send_message implements CcpField{
	apiKey(true), apiUrl(true), request(false), headers(true), response(false), httpStatus(false), date(false), tries(true)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_try_to_send_message(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

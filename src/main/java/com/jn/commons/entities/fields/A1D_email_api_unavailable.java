package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_email_api_unavailable implements CcpField{
		apiKey(true), apiUrl(true), request(true), headers(true), response(false), httpStatus(false), date(false)
	;
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_api_unavailable(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_http_api_retry_send_request implements CcpEntityField{
		url(true), method(true), headers(true), request(false), apiName(true), tries(true), response(false), status(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_http_api_retry_send_request(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
}

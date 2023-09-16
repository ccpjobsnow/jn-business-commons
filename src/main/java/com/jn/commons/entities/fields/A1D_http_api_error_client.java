package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_http_api_error_client implements CcpEntityField{
		url(true), method(true), headers(true), request(false), apiName(true),details(true), response(false), status(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_http_api_error_client(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
}

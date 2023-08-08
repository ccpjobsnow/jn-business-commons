package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_http_api_parameters implements CcpField{
	apiName(true), url(false), token (false), maxTries(false), sleep(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_http_api_parameters(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

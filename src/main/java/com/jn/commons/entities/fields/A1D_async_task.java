package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_async_task implements CcpEntityField{
	started(false), finished(false), topic(false), request(false), id(true), success(false), response(false)
	;

	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_async_task(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	

}


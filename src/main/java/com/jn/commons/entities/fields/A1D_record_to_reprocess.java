package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_record_to_reprocess implements CcpEntityField{
		date(true), operation(false),
		entity(true), id(true),
		json(false), status(false),
		reason(false), errorType(false)
	;

	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_record_to_reprocess(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	

}


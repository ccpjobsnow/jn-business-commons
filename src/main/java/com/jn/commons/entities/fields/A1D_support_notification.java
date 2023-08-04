package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_support_notification implements CcpField{
		type(true)
	;

	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_support_notification(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	

}


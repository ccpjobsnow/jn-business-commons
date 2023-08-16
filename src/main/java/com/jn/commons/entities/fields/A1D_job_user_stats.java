package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A1D_job_user_stats implements CcpField{
		email(true), balance(false), lastAccess(false), countAccess(false),
		openedTickets(false), closedTickets(false), balanceTransacionsCount(false)
	;
	
	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_job_user_stats(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

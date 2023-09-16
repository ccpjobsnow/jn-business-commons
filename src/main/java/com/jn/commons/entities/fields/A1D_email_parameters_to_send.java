package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_email_parameters_to_send implements CcpEntityField{
	email(false), sender(false), templateId(true), subjectType(false), moreParameters(false)
	;

	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_parameters_to_send(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	

}


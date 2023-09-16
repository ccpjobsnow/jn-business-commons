package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpEntityField;

public enum A1D_email_template_message implements CcpEntityField{
	templateId(true),language(true), subject(false), message(false)
	;

	private final boolean primaryKey;

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A1D_email_template_message(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	

}


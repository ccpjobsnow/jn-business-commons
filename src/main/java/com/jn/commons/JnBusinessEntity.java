package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.dependency.injection.CcpEspecification;
import com.ccp.especifications.db.crud.CcpDbCrud;

public enum JnBusinessEntity {
	professional_alias
	, recruiter
	, restriction_to_view_resume
	;
	@CcpEspecification
	CcpDbCrud crud;

	public CcpMapDecorator get(String id) {
		CcpMapDecorator oneById = this.crud.getOneById(id, this.name());
		return oneById;
	}

	public boolean exists(String id) {
		return this.crud.exists(id, this.name());
	}
	
}


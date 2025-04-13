package com.jn.mensageria;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityCrudOperationType;
import com.jn.entities.JnEntityAsyncTask;

class EntityCrud implements JnTopic{

	private final CcpEntity entity;
	
	public EntityCrud(CcpEntity entity) {
		this.entity = entity;

	}

	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		String operation = json.getAsString(JnEntityAsyncTask.Fields.operation.name());
		CcpEntityCrudOperationType valueOf = CcpEntityCrudOperationType.valueOf(operation);
		CcpJsonRepresentation apply = valueOf.execute(this.entity, json);
		return apply;
	}
	
}

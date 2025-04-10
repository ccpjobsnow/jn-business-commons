package com.ccp.jn.commons.mensageria;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityCrudOperationType;
import com.jn.commons.entities.JnEntityAsyncTask;

public class JnEntityTopic implements JnTopic{

	private final Function<CcpJsonRepresentation, CcpJsonRepresentation> function;
	
	public JnEntityTopic(CcpEntity entity, CcpJsonRepresentation json) {
		String operation = json.getAsString(JnEntityAsyncTask.Fields.operation.name());
		CcpEntityCrudOperationType valueOf = CcpEntityCrudOperationType.valueOf(operation);
		this.function = entity.getOperationCallback(valueOf);

	}

	public CcpJsonRepresentation apply(CcpJsonRepresentation t) {
		CcpJsonRepresentation apply = this.function.apply(t);
		return apply;
	}
	
}

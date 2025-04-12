package com.ccp.jn.commons.business;

import com.ccp.constantes.CcpOtherConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityTransferRecordToReverseEntity;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.jn.commons.entities.JnEntityLoginToken;
import com.jn.commons.utils.JnExecuteBulkOperation;

public class JnAsyncBusinessLockToken implements JnTopic {


	public static final JnAsyncBusinessLockToken INSTANCE = new JnAsyncBusinessLockToken();
	
	private JnAsyncBusinessLockToken() {
		
	}

	@SuppressWarnings("unchecked")
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		
		CcpEntityTransferRecordToReverseEntity registerLock = JnEntityLoginToken.ENTITY.getTransferRecordToReverseEntity();

		JnExecuteBulkOperation.INSTANCE.
		executeSelectUnionAllThenExecuteBulkOperation(
				json 
				, registerLock
				);
		
		return CcpOtherConstants.EMPTY_JSON;
	}

	

}

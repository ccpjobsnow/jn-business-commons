package com.ccp.jn.commons.business;

import com.ccp.constantes.CcpOtherConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityTransferRecordToReverseEntity;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.jn.commons.entities.JnEntityLoginPassword;
import com.jn.commons.utils.JnExecuteBulkOperation;

public class JnAsyncBusinessLockPassword implements JnTopic{


	public static final JnAsyncBusinessLockPassword INSTANCE = new JnAsyncBusinessLockPassword();
	
	private JnAsyncBusinessLockPassword() {
		
	}

	@SuppressWarnings("unchecked")
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		
		CcpEntityTransferRecordToReverseEntity registerLock = JnEntityLoginPassword.ENTITY.getTransferRecordToReverseEntity();
		JnExecuteBulkOperation.INSTANCE.
		executeSelectUnionAllThenExecuteBulkOperation(
				json 
				, registerLock
				);
		
		return CcpOtherConstants.EMPTY_JSON;
	}

	

}

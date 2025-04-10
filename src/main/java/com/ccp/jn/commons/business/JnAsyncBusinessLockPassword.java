package com.ccp.jn.commons.business;

import com.ccp.constantes.CcpOtherConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.jn.commons.entities.JnEntityLoginPassword;
import com.jn.commons.utils.JnCommonsExecuteBulkOperation;
import com.jn.commons.utils.TransferRecordToReverseEntity;

public class JnAsyncBusinessLockPassword implements JnTopic{


	public static final JnAsyncBusinessLockPassword INSTANCE = new JnAsyncBusinessLockPassword();
	
	private JnAsyncBusinessLockPassword() {
		
	}

	@SuppressWarnings("unchecked")
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		
		TransferRecordToReverseEntity registerLock = new TransferRecordToReverseEntity(JnEntityLoginPassword.ENTITY, CcpOtherConstants.DO_NOTHING, CcpOtherConstants.DO_NOTHING, CcpOtherConstants.DO_NOTHING, CcpOtherConstants.DO_NOTHING);
		JnCommonsExecuteBulkOperation.INSTANCE.
		executeSelectUnionAllThenExecuteBulkOperation(
				json 
				, registerLock
				);
		
		return CcpOtherConstants.EMPTY_JSON;
	}

	

}

package com.ccp.jn.commons.business;

import com.ccp.constantes.CcpOtherConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityTransferRecordToReverseEntity;
import com.ccp.jn.commons.actions.RegisterLogin;
import com.ccp.jn.commons.actions.RemoveAttempts;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.jn.commons.entities.JnEntityLoginPassword;
import com.jn.commons.entities.JnEntityLoginPasswordAttempts;
import com.jn.commons.entities.JnEntityLoginSessionValidation;
import com.jn.commons.utils.JnExecuteBulkOperation;

public class JnAsyncBusinessExecuteLogin implements JnTopic {

	public static final JnAsyncBusinessExecuteLogin INSTANCE = new JnAsyncBusinessExecuteLogin();
	
	private JnAsyncBusinessExecuteLogin() {}
	
	@SuppressWarnings("unchecked")
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		CcpJsonRepresentation renameField = json.renameField("sessionToken", JnEntityLoginSessionValidation.Fields.token.name());
		
		CcpEntity twinEntity = JnEntityLoginPassword.ENTITY.getTwinEntity();
		CcpEntityTransferRecordToReverseEntity executeUnlock = twinEntity.getTransferRecordToReverseEntity();
		CcpEntity entityAttempts = JnEntityLoginPasswordAttempts.ENTITY;
		RemoveAttempts removeAttempts = new RemoveAttempts(entityAttempts);

		JnExecuteBulkOperation.INSTANCE.
		executeSelectUnionAllThenExecuteBulkOperation(
				renameField 
				, executeUnlock
				, removeAttempts
				, RegisterLogin.INSTANCE
				);
		
		return CcpOtherConstants.EMPTY_JSON;
	}

}

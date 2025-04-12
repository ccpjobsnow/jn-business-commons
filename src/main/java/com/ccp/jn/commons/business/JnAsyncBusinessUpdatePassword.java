package com.ccp.jn.commons.business;

import com.ccp.constantes.CcpOtherConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityTransferRecordToReverseEntity;
import com.ccp.jn.commons.actions.RegisterLogin;
import com.ccp.jn.commons.actions.RemoveAttempts;
import com.ccp.jn.commons.actions.SolveLoginConflict;
import com.ccp.jn.commons.actions.UpdatePassword;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.jn.commons.entities.JnEntityLoginPassword;
import com.jn.commons.entities.JnEntityLoginPasswordAttempts;
import com.jn.commons.entities.JnEntityLoginSessionValidation;
import com.jn.commons.utils.JnExecuteBulkOperation;

public class JnAsyncBusinessUpdatePassword implements JnTopic {

	public static final JnAsyncBusinessUpdatePassword INSTANCE = new JnAsyncBusinessUpdatePassword();
	
	private JnAsyncBusinessUpdatePassword() {}

	@SuppressWarnings("unchecked")
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {

		CcpEntityTransferRecordToReverseEntity executeLogout = JnEntityLoginSessionValidation.ENTITY.getTransferRecordToReverseEntity();
		
		CcpEntity twinEntity = JnEntityLoginPassword.ENTITY.getTwinEntity();
		CcpEntityTransferRecordToReverseEntity registerUnlock = twinEntity.getTransferRecordToReverseEntity();
		RemoveAttempts removeAttempts = new RemoveAttempts(JnEntityLoginPasswordAttempts.ENTITY);

		CcpJsonRepresentation renameField = json.renameField("sessionToken", JnEntityLoginSessionValidation.Fields.token.name());
		JnExecuteBulkOperation.INSTANCE.
		executeSelectUnionAllThenExecuteBulkOperation(
				renameField 
				, UpdatePassword.INSTANCE
				, registerUnlock
				, removeAttempts
				, executeLogout
				, RegisterLogin.INSTANCE
				, SolveLoginConflict.INSTANCE
				);
		
		return CcpOtherConstants.EMPTY_JSON;
	}
}

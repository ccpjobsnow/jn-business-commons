package com.ccp.jn.commons.business;

import com.ccp.constantes.CcpOtherConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.jn.commons.actions.RegisterLogin;
import com.ccp.jn.commons.actions.RemoveAttempts;
import com.ccp.jn.commons.actions.SolveLoginConflict;
import com.ccp.jn.commons.actions.UpdatePassword;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.jn.commons.entities.JnEntityLoginPassword;
import com.jn.commons.entities.JnEntityLoginPasswordAttempts;
import com.jn.commons.entities.JnEntityLoginSessionValidation;
import com.jn.commons.utils.JnCommonsExecuteBulkOperation;
import com.jn.commons.utils.TransferRecordToReverseEntity;

public class JnAsyncBusinessUpdatePassword implements JnTopic {

	public static final JnAsyncBusinessUpdatePassword INSTANCE = new JnAsyncBusinessUpdatePassword();
	
	private JnAsyncBusinessUpdatePassword() {}

	@SuppressWarnings("unchecked")
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {

		TransferRecordToReverseEntity executeLogout = new TransferRecordToReverseEntity(JnEntityLoginSessionValidation.ENTITY, CcpOtherConstants.DO_NOTHING, CcpOtherConstants.DO_NOTHING, CcpOtherConstants.DO_NOTHING, CcpOtherConstants.DO_NOTHING);
		
		CcpEntity twinEntity = JnEntityLoginPassword.ENTITY.getTwinEntity();
		TransferRecordToReverseEntity registerUnlock = new TransferRecordToReverseEntity(twinEntity, CcpOtherConstants.DO_NOTHING, CcpOtherConstants.DO_NOTHING, CcpOtherConstants.DO_NOTHING, CcpOtherConstants.DO_NOTHING);
		RemoveAttempts removeAttempts = new RemoveAttempts(JnEntityLoginPasswordAttempts.ENTITY);

		CcpJsonRepresentation renameField = json.renameField("sessionToken", JnEntityLoginSessionValidation.Fields.token.name());
		JnCommonsExecuteBulkOperation.INSTANCE.
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

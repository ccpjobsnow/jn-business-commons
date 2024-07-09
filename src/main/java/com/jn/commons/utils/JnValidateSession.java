package com.jn.commons.utils;

import java.util.function.Function;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.crud.CcpGetEntityId;
import com.jn.commons.entities.JnEntityLoginEmail;
import com.jn.commons.entities.JnEntityLoginPassword;
import com.jn.commons.entities.JnEntityLoginSessionToken;
import com.jn.commons.entities.JnEntityLoginToken;
import com.jn.commons.status.StatusExecuteLogin;

public class JnValidateSession implements Function<CcpJsonRepresentation, CcpJsonRepresentation>{

	private JnValidateSession() {}
	
	public static final JnValidateSession INSTANCE = new JnValidateSession();
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		CcpJsonRepresentation transformed = json.getTransformed(JnTransformEmailHash.INSTANCE);
		new CcpGetEntityId(transformed)
		.toBeginProcedureAnd()
			.ifThisIdIsPresentInEntity(JnEntityLoginToken.INSTANCE.getMirrorEntity()).returnStatus(StatusExecuteLogin.lockedToken).and()
			.ifThisIdIsPresentInEntity(JnEntityLoginPassword.INSTANCE.getMirrorEntity()).returnStatus(StatusExecuteLogin.lockedPassword).and()
			.ifThisIdIsNotPresentInEntity(JnEntityLoginPassword.INSTANCE).returnStatus(StatusExecuteLogin.missingPassword).and()
			.ifThisIdIsNotPresentInEntity(JnEntityLoginEmail.INSTANCE).returnStatus(StatusExecuteLogin.missingEmail).and()
			.ifThisIdIsNotPresentInEntity(JnEntityLoginSessionToken.INSTANCE).returnStatus(StatusExecuteLogin.invalidSession).andFinallyReturningThisFields("sessionToken")
		.endThisProcedureRetrievingTheResultingData(CcpConstants.DO_NOTHING);
		return json;
	}

}

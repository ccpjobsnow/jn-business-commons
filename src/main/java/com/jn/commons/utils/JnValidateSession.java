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
		try {
			
			CcpJsonRepresentation transformed = json.putEmailHash("SHA1");
			new CcpGetEntityId(transformed)
			.toBeginProcedureAnd()
			.ifThisIdIsPresentInEntity(JnEntityLoginToken.ENTITY.getTwinEntity()).returnStatus(StatusExecuteLogin.lockedToken).and()
			.ifThisIdIsPresentInEntity(JnEntityLoginPassword.ENTITY.getTwinEntity()).returnStatus(StatusExecuteLogin.lockedPassword).and()
			.ifThisIdIsNotPresentInEntity(JnEntityLoginPassword.ENTITY).returnStatus(StatusExecuteLogin.missingPassword).and()
			.ifThisIdIsNotPresentInEntity(JnEntityLoginEmail.ENTITY).returnStatus(StatusExecuteLogin.missingEmail).and()
			.ifThisIdIsNotPresentInEntity(JnEntityLoginSessionToken.ENTITY).returnStatus(StatusExecuteLogin.invalidSession).andFinallyReturningThisFields("sessionToken")
			.endThisProcedureRetrievingTheResultingData(CcpConstants.DO_NOTHING);
			return transformed;
		} catch (Exception e) {
			return json;
		}
	}

}

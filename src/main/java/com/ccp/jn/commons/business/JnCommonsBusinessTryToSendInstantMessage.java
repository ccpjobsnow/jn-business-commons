package com.ccp.jn.commons.business;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.jn.commons.entities.JnEntityInstantMessengerParametersToSend;

public class JnCommonsBusinessTryToSendInstantMessage implements JnTopic {
	
	public static final JnCommonsBusinessTryToSendInstantMessage INSTANCE = new JnCommonsBusinessTryToSendInstantMessage();
	
	private JnCommonsBusinessTryToSendInstantMessage() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		CcpJsonRepresentation instantMessengerData = JnCommonsSendHttpRequest.INSTANCE.execute(json, x -> JnCommonsSendInstantMessage.INSTANCE.apply(x), JnCommonsHttpRequestType.instantMessenger, JnEntityInstantMessengerParametersToSend.Fields.subjectType.name());
		return instantMessengerData;
	}

}

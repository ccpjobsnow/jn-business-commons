package com.jn.business;

import com.ccp.decorators.CcpJsonRepresentation;
import com.jn.entities.JnEntityInstantMessengerParametersToSend;
import com.jn.mensageria.JnTopic;

public class JnBusinessTryToSendInstantMessage implements JnTopic {
	
	public static final JnBusinessTryToSendInstantMessage INSTANCE = new JnBusinessTryToSendInstantMessage();
	
	private JnBusinessTryToSendInstantMessage() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		CcpJsonRepresentation instantMessengerData = JnBusinessSendHttpRequest.INSTANCE.execute(json, x -> JnBusinessSendInstantMessage.INSTANCE.apply(x), JnBusinessHttpRequestType.instantMessenger, JnEntityInstantMessengerParametersToSend.Fields.subjectType.name());
		return instantMessengerData;
	}

}

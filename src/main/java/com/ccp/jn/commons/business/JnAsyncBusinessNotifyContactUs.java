package com.ccp.jn.commons.business;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.ccp.jn.commons.messages.JnCommonsSendMessage;
import com.jn.commons.entities.JnEntityContactUs;

public class JnAsyncBusinessNotifyContactUs implements JnTopic{

	public static final JnAsyncBusinessNotifyContactUs INSTANCE = new JnAsyncBusinessNotifyContactUs();
	
	private JnAsyncBusinessNotifyContactUs() {
		
	}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {

		JnCommonsSendMessage x = new JnCommonsSendMessage();
		JnCommonsNotifySupport.INSTANCE.apply(json, JnAsyncBusinessNotifyContactUs.class.getName(), JnEntityContactUs.ENTITY, x);
		
		return json;
	}
	

}

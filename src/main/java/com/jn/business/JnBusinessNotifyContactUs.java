package com.jn.business;

import com.ccp.decorators.CcpJsonRepresentation;
import com.jn.entities.JnEntityContactUs;
import com.jn.mensageria.JnTopic;
import com.jn.messages.JnSendMessage;

public class JnBusinessNotifyContactUs implements JnTopic{

	public static final JnBusinessNotifyContactUs INSTANCE = new JnBusinessNotifyContactUs();
	
	private JnBusinessNotifyContactUs() {
		
	}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {

		JnSendMessage x = new JnSendMessage();
		JnBusinessNotifySupport.INSTANCE.apply(json, JnBusinessNotifyContactUs.class.getName(), JnEntityContactUs.ENTITY, x);
		
		return json;
	}
	

}

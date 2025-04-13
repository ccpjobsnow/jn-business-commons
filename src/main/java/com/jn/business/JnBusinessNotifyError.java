package com.jn.business;

import com.ccp.decorators.CcpJsonRepresentation;
import com.jn.entities.JnEntityJobsnowError;
import com.jn.mensageria.JnTopic;
import com.jn.messages.JnSendMessage;
import com.jn.messages.JnSendMessageIgnoringProcessErrors;

public class JnBusinessNotifyError implements JnTopic{

	public static final JnBusinessNotifyError INSTANCE = new JnBusinessNotifyError();
	
	private JnBusinessNotifyError() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		
		String name = JnBusinessNotifyError.class.getName();
		JnSendMessage x = new JnSendMessageIgnoringProcessErrors();
		JnBusinessNotifySupport.INSTANCE.apply(json, name, JnEntityJobsnowError.ENTITY, x);

		return json;
	}
	
	public CcpJsonRepresentation apply(Throwable e) {
		
		CcpJsonRepresentation json = new CcpJsonRepresentation(e);
		
		CcpJsonRepresentation execute = this.apply(json);
		return execute;
	}

}

package com.ccp.jn.commons.business;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.ccp.jn.commons.messages.JnCommonsSendMessage;
import com.ccp.jn.commons.messages.JnCommonsSendMessageIgnoringProcessErrors;
import com.jn.commons.entities.JnEntityJobsnowError;

public class JnAsyncBusinessNotifyError implements JnTopic{

	public static final JnAsyncBusinessNotifyError INSTANCE = new JnAsyncBusinessNotifyError();
	
	private JnAsyncBusinessNotifyError() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		
		String name = JnAsyncBusinessNotifyError.class.getName();
		JnCommonsSendMessage x = new JnCommonsSendMessageIgnoringProcessErrors();
		JnCommonsNotifySupport.INSTANCE.apply(json, name, JnEntityJobsnowError.ENTITY, x);

		return json;
	}
	
	public CcpJsonRepresentation apply(Throwable e) {
		
		CcpJsonRepresentation json = new CcpJsonRepresentation(e);
		
		CcpJsonRepresentation execute = this.apply(json);
		return execute;
	}

}

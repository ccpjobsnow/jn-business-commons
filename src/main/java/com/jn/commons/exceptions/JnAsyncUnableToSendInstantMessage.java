package com.jn.commons.exceptions;

import com.ccp.decorators.CcpJsonRepresentation;

@SuppressWarnings("serial")
public class JnAsyncUnableToSendInstantMessage extends RuntimeException{

	public JnAsyncUnableToSendInstantMessage(CcpJsonRepresentation json) {
		super("This message couldn't be sent. Details: " + json);
	}
	
}

package com.jn.commons.exceptions;

import com.ccp.decorators.CcpJsonRepresentation;

@SuppressWarnings("serial")
public class JnCommonsUnableToSendInstantMessage extends RuntimeException{

	public JnCommonsUnableToSendInstantMessage(CcpJsonRepresentation json) {
		super("This message couldn't be sent. Details: " + json);
	}
	
}

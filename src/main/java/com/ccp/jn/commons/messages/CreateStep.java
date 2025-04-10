package com.ccp.jn.commons.messages;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;

public class CreateStep {

	final JnCommonsSendMessage getMessage;

	CreateStep(JnCommonsSendMessage getMessage) {
		this.getMessage = getMessage;
	}
	
	public WithTheProcess withTheProcess(Function<CcpJsonRepresentation, CcpJsonRepresentation> process) {
		return new WithTheProcess(this, process);
	}
}

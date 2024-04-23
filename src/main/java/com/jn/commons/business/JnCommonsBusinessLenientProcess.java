package com.jn.commons.business;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;

public class JnCommonsBusinessLenientProcess implements Function<CcpJsonRepresentation, CcpJsonRepresentation>{

	private final Function<CcpJsonRepresentation, CcpJsonRepresentation> process;

	public JnCommonsBusinessLenientProcess(Function<CcpJsonRepresentation, CcpJsonRepresentation> process) {
		this.process = process;
	}

	@Override
	public CcpJsonRepresentation apply(CcpJsonRepresentation t) {
		try {
			CcpJsonRepresentation apply = this.process.apply(t);
			return apply;
		} catch (Exception e) {
			return t;
		}
	}
	
	
	
}

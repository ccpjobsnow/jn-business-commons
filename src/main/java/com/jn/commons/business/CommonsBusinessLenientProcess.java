package com.jn.commons.business;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;

public class CommonsBusinessLenientProcess implements Function<CcpJsonRepresentation, CcpJsonRepresentation>{

	private final Function<CcpJsonRepresentation, CcpJsonRepresentation> process;

	public CommonsBusinessLenientProcess(Function<CcpJsonRepresentation, CcpJsonRepresentation> process) {
		this.process = process;
	}

	
	public CcpJsonRepresentation apply(CcpJsonRepresentation t) {
		try {
			CcpJsonRepresentation apply = this.process.apply(t);
			return apply;
		} catch (Exception e) {
			e.printStackTrace();
			return t;
		}
	}
	
	
	
}

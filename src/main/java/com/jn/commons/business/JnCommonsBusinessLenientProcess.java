package com.jn.commons.business;

import java.util.function.Function;

import com.ccp.decorators.CcpMapDecorator;

public class JnCommonsBusinessLenientProcess implements Function<CcpMapDecorator, CcpMapDecorator>{

	private final Function<CcpMapDecorator, CcpMapDecorator> process;

	public JnCommonsBusinessLenientProcess(Function<CcpMapDecorator, CcpMapDecorator> process) {
		this.process = process;
	}

	@Override
	public CcpMapDecorator apply(CcpMapDecorator t) {
		try {
			CcpMapDecorator apply = this.process.apply(t);
			return apply;
		} catch (Exception e) {
			 return t;
		}
	}
	
	
	
}

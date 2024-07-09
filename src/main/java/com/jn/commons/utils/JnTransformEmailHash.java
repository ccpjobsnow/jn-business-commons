package com.jn.commons.utils;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;

public class JnTransformEmailHash implements Function<CcpJsonRepresentation, CcpJsonRepresentation>{

	public static final JnTransformEmailHash INSTANCE = new JnTransformEmailHash();

	private JnTransformEmailHash() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		String email = json.getAsString("email");
		String hash = new CcpStringDecorator(email).hash().asString("SHA1");
		CcpJsonRepresentation put = json.put("email", hash).put("originalEmail", email);
		return put;
	}
}

package com.jn.json.transformers;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.password.CcpPasswordHandler;
import com.jn.entities.JnEntityLoginPassword;

public class JnJsonTransformerPutPasswordSecret implements Function<CcpJsonRepresentation, CcpJsonRepresentation> {

	private JnJsonTransformerPutPasswordSecret() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {

		String token = json.getAsString(JnEntityLoginPassword.Fields.password.name());
		
		CcpPasswordHandler dependency = CcpDependencyInjection.getDependency(CcpPasswordHandler.class);
		
		String passwordHash = dependency.getHash(token); 
		
		CcpJsonRepresentation put = json.put(JnEntityLoginPassword.Fields.password.name(), passwordHash);
		
		return put;
	}

}

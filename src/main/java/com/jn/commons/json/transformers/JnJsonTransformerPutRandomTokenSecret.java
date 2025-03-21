package com.jn.commons.json.transformers;

import java.util.function.Function;

import com.ccp.constantes.CcpStringConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.decorators.CcpTextDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.password.CcpPasswordHandler;
import com.jn.commons.entities.JnEntityLoginToken;

public class JnJsonTransformerPutRandomTokenSecret implements Function<CcpJsonRepresentation, CcpJsonRepresentation> {

	private JnJsonTransformerPutRandomTokenSecret() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		String createOriginalToken = this.createOriginalToken();
		String originalToken = json.getOrDefault("originalToken", createOriginalToken);
		 
		CcpPasswordHandler dependency = CcpDependencyInjection.getDependency(CcpPasswordHandler.class);
		
		String token = dependency.getHash(originalToken);
	
		CcpJsonRepresentation put = json
				.put(JnEntityLoginToken.Fields.token.name(), token)
				.put("originalToken", originalToken)
				;
		
		return put;
	}

	private String createOriginalToken() {
		CcpStringDecorator csd = new CcpStringDecorator(CcpStringConstants.CHARACTERS_TO_GENERATE_TOKEN.value);
		CcpTextDecorator text = csd.text();
		CcpTextDecorator generateToken = text.generateToken(8);
		String originalToken = generateToken.content;
		return originalToken;
	}

}

package com.jn.commons.json.transformers;

import java.util.function.Function;

import com.ccp.constantes.CcpStringConstants;
import com.ccp.decorators.CcpHashDecorator;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.decorators.CcpTextDecorator;
import com.ccp.utils.CcpHashAlgorithm;
import com.jn.commons.entities.JnEntityLoginSessionValidation;
import com.jn.commons.entities.JnEntityLoginToken;

public class JnJsonTransformerPutRandomTokenHash implements Function<CcpJsonRepresentation, CcpJsonRepresentation> {

	private JnJsonTransformerPutRandomTokenHash() {}
	
	public static final JnJsonTransformerPutRandomTokenHash INSTANCE = new JnJsonTransformerPutRandomTokenHash();
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		
		String originalToken = json.getOrDefault(JnEntityLoginSessionValidation.Fields.token.name(), this.getOriginalToken());
		CcpHashDecorator hash = new CcpStringDecorator(originalToken).hash();
		
		String token = hash.asString(CcpHashAlgorithm.SHA1);
	
		CcpJsonRepresentation put = json
				.put(JnEntityLoginToken.Fields.token.name(), token)
				.put("originalToken", originalToken)
				;
		
		return put;
	}

	private String getOriginalToken() {
		CcpStringDecorator csd = new CcpStringDecorator(CcpStringConstants.CHARACTERS_TO_GENERATE_TOKEN.value);
		CcpTextDecorator text = csd.text();
		CcpTextDecorator generateToken = text.generateToken(8);
		String originalToken = generateToken.content;
		return originalToken;
	}

}

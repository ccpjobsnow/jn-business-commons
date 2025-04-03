package com.jn.commons.json.transformers;

import java.util.function.Function;

import com.ccp.constantes.CcpStringConstants;
import com.ccp.decorators.CcpEmailDecorator;
import com.ccp.decorators.CcpHashDecorator;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.utils.CcpHashAlgorithm;
import com.jn.commons.exceptions.JnCommonsIsNotAnEmail;

public class JnJsonTransformerPutEmailHash implements Function<CcpJsonRepresentation, CcpJsonRepresentation> {

	private JnJsonTransformerPutEmailHash() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		String oldField = CcpStringConstants.EMAIL.value;
		String newField = "originalEmail";
		String value = json.getAsString(oldField);
		CcpEmailDecorator email = new CcpStringDecorator(value).email();
		
		boolean isNotAnEmail = email.isValid() == false;
		
		if(isNotAnEmail) {
			throw new JnCommonsIsNotAnEmail(value, json);
		}
		
		CcpHashDecorator hash2 = email.hash();
		String hash = hash2.asString(CcpHashAlgorithm.SHA1);
		CcpJsonRepresentation put2 = json.put(oldField, hash);
		CcpJsonRepresentation put = put2.put(newField, value);
		return put;
	}

}

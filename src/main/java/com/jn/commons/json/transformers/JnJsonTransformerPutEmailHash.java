package com.jn.commons.json.transformers;

import java.util.function.Function;

import com.ccp.constantes.CcpStringConstants;
import com.ccp.decorators.CcpHashDecorator;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.utils.CcpHashAlgorithm;

public class JnJsonTransformerPutEmailHash implements Function<CcpJsonRepresentation, CcpJsonRepresentation> {

	public final static JnJsonTransformerPutEmailHash INSTANCE = new JnJsonTransformerPutEmailHash();

	private JnJsonTransformerPutEmailHash() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		String oldField = CcpStringConstants.EMAIL.value;
		String newField = "originalEmail";
		String value = json.getAsString(oldField);
		CcpHashDecorator hash2 = new CcpStringDecorator(value).hash();
		String hash = hash2.asString(CcpHashAlgorithm.SHA1);
		CcpJsonRepresentation put2 = json.put(oldField, hash);
		CcpJsonRepresentation put = put2.put(newField, value);
		return put;
	}

}

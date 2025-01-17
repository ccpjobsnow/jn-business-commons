package com.jn.commons.json.transformers;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpTimeDecorator;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgableOptions;

public class JnJsonTransformerPutFormattedCurrentDateAndCurrentTimeStamp implements Function<CcpJsonRepresentation, CcpJsonRepresentation> {

	private JnJsonTransformerPutFormattedCurrentDateAndCurrentTimeStamp() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		CcpTimeDecorator ctd = new CcpTimeDecorator();
		String formattedDateTime = ctd.getFormattedDateTime(CcpEntityExpurgableOptions.millisecond.format);
		boolean containsAllFields = json.containsAllFields(CcpEntityField.TIMESTAMP.name());
		
		if(containsAllFields) {
			return json;
		}
		
		CcpJsonRepresentation put = json.put(CcpEntityField.TIMESTAMP.name(), ctd.content).put(CcpEntityField.DATE.name(), formattedDateTime);
		
		return put;
	}

}

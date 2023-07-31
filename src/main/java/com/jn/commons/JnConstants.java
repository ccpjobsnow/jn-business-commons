package com.jn.commons;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.process.CcpProcess;

public interface JnConstants {
	CcpProcess PUT_EMAIL_TOKEN = values -> values.put("token", new CcpStringDecorator(CcpConstants.CHARACTERS_TO_GENERATE_TOKEN).text().generateToken(8));
	String RESUMES_BUCKET = "JN_RESUMES";
	String TENANT = "JN_TENANT";
	String DOT = ".";
	int ONE_HOUR_IN_SECONDS = 60 * 60;
	String SLASH = "/";
	
}

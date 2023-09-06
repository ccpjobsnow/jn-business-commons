package com.jn.commons;

import java.io.File;
import java.util.function.Function;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpMapDecorator;
import com.ccp.decorators.CcpStringDecorator;


public interface JnConstants {
	Function<CcpMapDecorator, CcpMapDecorator> PUT_EMAIL_TOKEN = values -> values.put("token", new CcpStringDecorator(CcpConstants.CHARACTERS_TO_GENERATE_TOKEN).text().generateToken(8));
	String RESUMES_BUCKET = "JN_RESUMES";
	String TENANT = "JN_TENANT";
	String DOT = ".";
	int ONE_HOUR_IN_SECONDS = 60 * 60;
	String SLASH = "/";
	int maxTries = 3;
	String DATABASE_SCRIPTS_FOLDER = new File("").getAbsolutePath() +  "/../jn-documentation/database/elasticsearch/scripts/";
	String DB_URL = "https://34.139.142.228:9200";
}

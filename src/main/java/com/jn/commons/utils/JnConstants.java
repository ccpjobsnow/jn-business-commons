package com.jn.commons.utils;

import java.util.function.Function;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.password.CcpPasswordHandler;


public interface JnConstants {
	Function<CcpJsonRepresentation, CcpJsonRepresentation> PUT_EMAIL_TOKEN = values -> values.put("token", new CcpStringDecorator(CcpConstants.CHARACTERS_TO_GENERATE_TOKEN).text().generateToken(8).content);
	Function<CcpJsonRepresentation, CcpJsonRepresentation> PUT_PASSWORD = values -> {
		CcpJsonRepresentation apply = PUT_EMAIL_TOKEN.apply(values);
		String token = apply.getAsString("token");
		CcpPasswordHandler passwordHandler = CcpDependencyInjection.getDependency(CcpPasswordHandler.class);
		String passwordHash = passwordHandler.getPasswordHash(token);
		CcpJsonRepresentation put = values.put("password", passwordHash).put("token", token);
		return put;
	};	
	String DOT = ".";
	int ONE_HOUR_IN_SECONDS = 60 * 60;
	String SLASH = "/";
	int maxTries = 3;
}

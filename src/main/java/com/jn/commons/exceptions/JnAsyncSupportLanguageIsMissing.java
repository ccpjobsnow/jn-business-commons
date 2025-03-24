package com.jn.commons.exceptions;

@SuppressWarnings("serial")
public class JnAsyncSupportLanguageIsMissing extends RuntimeException {
	public JnAsyncSupportLanguageIsMissing() {
		super("It is missing the configuration 'supportLanguage'");
	}
}

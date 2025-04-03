package com.jn.commons.exceptions;

@SuppressWarnings("serial")
public class JnCommonsSupportLanguageIsMissing extends RuntimeException {
	public JnCommonsSupportLanguageIsMissing() {
		super("It is missing the configuration 'supportLanguage'");
	}
}

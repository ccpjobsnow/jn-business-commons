package com.jn.commons.exceptions;

@SuppressWarnings("serial")
public class JnInvalidTopic extends RuntimeException {

	public JnInvalidTopic(String processName) {
		super("The process '" + processName + "' is an invalid topic");
	}
}

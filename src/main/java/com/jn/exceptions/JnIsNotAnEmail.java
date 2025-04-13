package com.jn.exceptions;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.exceptions.db.utils.CcpEntityCalculateIdError;

@SuppressWarnings("serial")
public class JnIsNotAnEmail extends CcpEntityCalculateIdError {
	public JnIsNotAnEmail(String content, CcpJsonRepresentation json) {
		super("The text '" + content + "' is not a valid email in the json " + json);
	}
}

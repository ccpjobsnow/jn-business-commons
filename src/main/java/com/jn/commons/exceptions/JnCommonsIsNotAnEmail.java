package com.jn.commons.exceptions;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.exceptions.db.utils.CcpEntityCalculateIdError;

@SuppressWarnings("serial")
public class JnCommonsIsNotAnEmail extends CcpEntityCalculateIdError {
	public JnCommonsIsNotAnEmail(String content, CcpJsonRepresentation json) {
		super("The text '" + content + "' is not a valid email in the json " + json);
	}
}

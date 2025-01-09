package com.jn.commons.json.validations;

import com.ccp.constantes.CcpStringConstants;
import com.ccp.validation.annotations.CcpJsonFieldsValidation;
import com.ccp.validation.annotations.Regex;

@CcpJsonFieldsValidation(
		regex = {
				@Regex(value = CcpStringConstants.EMAIL_REGEX, fields = "email")
		})
public class JnJsonValidationEmail {

}

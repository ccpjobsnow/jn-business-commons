package com.jn.commons.validations;

import com.ccp.constantes.CcpConstants;
import com.ccp.validation.annotations.Regex;
import com.ccp.validation.annotations.CcpJsonFieldsValidation;

@CcpJsonFieldsValidation(
		regex = {
				@Regex(value = CcpConstants.EMAIL_REGEX, fields = "email")
		})
public class JsonFieldsValidationJnEmail {

}

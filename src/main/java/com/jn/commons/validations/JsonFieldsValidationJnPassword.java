package com.jn.commons.validations;

import com.ccp.constantes.CcpConstants;
import com.ccp.validation.annotations.ObjectTextSize;
import com.ccp.validation.annotations.Regex;
import com.ccp.validation.annotations.SimpleObject;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.ObjectTextSizeValidations;
import com.ccp.validation.enums.SimpleObjectValidations;

@ValidationRules(
		regex = {
				@Regex(value = CcpConstants.STRONG_PASSWORD_REGEX, fields = "password")
		},
		simpleObject = { 
		@SimpleObject(rule = SimpleObjectValidations.requiredFields, fields = { "password" }) },
		objectTextSize  = {
				@ObjectTextSize(rule = ObjectTextSizeValidations.equalsOrGreaterThan, fields = { "password"}, bound = 8) }
)
public class JsonFieldsValidationJnPassword {

}

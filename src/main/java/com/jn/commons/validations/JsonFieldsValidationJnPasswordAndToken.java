package com.jn.commons.validations;

import com.ccp.constantes.CcpConstants;
import com.ccp.validation.annotations.ObjectTextSize;
import com.ccp.validation.annotations.Regex;
import com.ccp.validation.annotations.SimpleObject;
import com.ccp.validation.annotations.CcpJsonValidation;
import com.ccp.validation.enums.ObjectTextSizeValidations;
import com.ccp.validation.enums.SimpleObjectValidations;

@CcpJsonValidation(
		regex = {
				@Regex(value = CcpConstants.STRONG_PASSWORD_REGEX, fields = "password")
		},
		simpleObject = {
		@SimpleObject(rule = SimpleObjectValidations.requiredFields, fields = { "password", "token" }) },

		objectTextSize  = {
				@ObjectTextSize(rule = ObjectTextSizeValidations.equalsTo, fields = { "token" }, bound = 8) 
				,@ObjectTextSize(rule = ObjectTextSizeValidations.equalsOrGreaterThan, fields = { "password"}, bound = 8) 
				,@ObjectTextSize(rule = ObjectTextSizeValidations.equalsOrLessThan, fields = { "password"}, bound = 20) }

)
public class JsonFieldsValidationJnPasswordAndToken {

}

package com.jn.commons.validations;

import com.ccp.validation.annotations.AllowedValues;
import com.ccp.validation.annotations.SimpleObject;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.AllowedValuesValidations;
import com.ccp.validation.enums.SimpleObjectValidations;

@ValidationRules(simpleObject = {
		@SimpleObject(fields = { "json", "operation" }, rule = SimpleObjectValidations.requiredFields) },

		allowedValues = { @AllowedValues(fields = "operation", allowedValues = { "create", "update",
				"delete" }, rule = AllowedValuesValidations.objectWithAllowedTexts) }

)
public class JsonFieldsValidationJnAudit {

}

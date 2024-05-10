package com.jn.commons.validations;

import com.ccp.validation.annotations.AllowedValues;
import com.ccp.validation.annotations.SimpleObject;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.AllowedValuesValidations;
import com.ccp.validation.enums.SimpleObjectValidations;

@ValidationRules(simpleObject = {
		@SimpleObject(rule = SimpleObjectValidations.requiredFields, fields = { "channel", "goal" }) },
		allowedValues = {
				@AllowedValues(rule = AllowedValuesValidations.arrayWithAllowedTexts, fields = {
						"goal" }, allowedValues = { "jobs", "recruiting" }),
				@AllowedValues(rule = AllowedValuesValidations.objectWithAllowedTexts, fields = {
						"channel" }, allowedValues = { "linkedin", "telegram", "friends", "others" }), }

)
public class JsonFieldsValidationJnLoginAnswers {

}

package com.jn.commons.validations;

import com.ccp.validation.annotations.ObjectTextSize;
import com.ccp.validation.annotations.SimpleObject;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.ObjectTextSizeValidations;
import com.ccp.validation.enums.SimpleObjectValidations;

@ValidationRules(
		simpleObject = {
				@SimpleObject(
						fields = {"topic", "request", "success", "response"},
						rule = SimpleObjectValidations.requiredFields
						)
		}
//		,
//		
//		objectTextSize = {
//				@ObjectTextSize(
//						fields = {"topic", "request", "response"},
//						rule = ObjectTextSizeValidations.equalsOrGreaterThan
//						)
//		}
		)

public class JsonFieldsValidationAsyncTask {

}

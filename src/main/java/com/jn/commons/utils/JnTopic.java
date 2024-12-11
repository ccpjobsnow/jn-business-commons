package com.jn.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ccp.validation.annotations.SimpleObject;
import com.ccp.validation.annotations.CcpJsonFieldsValidation;
import com.ccp.validation.enums.SimpleObjectValidations;

public interface JnTopic {
	String name();
	Class<?> validationClass();
	
	default String[] getFields() {
		Class<?> clazz = this.validationClass();
		CcpJsonFieldsValidation rules = clazz.getAnnotation(CcpJsonFieldsValidation.class);
		ArrayList<String> list = new ArrayList<>();
		if(rules == null) {
			return new String[] {};
		}
		SimpleObject[] simpleObject = rules.simpleObject();
		for (SimpleObject so : simpleObject) {
			SimpleObjectValidations rule = so.rule();
			boolean requiredFields = SimpleObjectValidations.requiredFields.equals(rule);
			if(requiredFields) {
				String[] fields = so.fields();
				List<String> asList = Arrays.asList(fields);
				list.addAll(asList);
			}
			boolean requiredAtLeastOne = SimpleObjectValidations.requiredAtLeastOne.equals(rule);
			if(requiredAtLeastOne) {
				String[] fields = so.fields();
				List<String> asList = Arrays.asList(fields);
				list.addAll(asList);
			}
		}
		
		String[] array = list.toArray(new String[list.size()]);
		return array;
	}
	default boolean canSave() {
		return true;
	}
}

package com.jn.commons.utils;

public interface JnTopic {
	String name();
	Class<?> validationClass();
	
	default boolean canSave() {
		return true;
	}
}

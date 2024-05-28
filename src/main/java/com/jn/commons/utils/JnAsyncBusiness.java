package com.jn.commons.utils;

public enum JnAsyncBusiness implements JnTopic{
	executeLogout, 
	updatePassword, 
	executeLogin, 
	sendUserToken, 
	notifyError, 
	notifyContactUs, 
	grouperBalance, 
	grouperSupport, 
	grouperLogin, 
	sendInstantMessage, 
	sendEmailMessage, 
	lockPassword, 
	lockToken,
	;

	@Override
	public Class<?> validationClass() {
		Class<? extends JnAsyncBusiness> class1 = this.getClass();
		return class1;
	}
}

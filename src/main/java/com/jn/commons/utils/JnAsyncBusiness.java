package com.jn.commons.utils;

public enum JnAsyncBusiness implements JnTopic{
	deleteKeysFromCache(false),
	sendInstantMessage(true), 
	sendEmailMessage(true), 
	notifyContactUs(true), 
	grouperBalance(true), 
	updatePassword(true), 
	grouperSupport(true), 
	executeLogout(true), 
	sendUserToken(true), 
	executeLogin(true), 
	notifyError(true), 
	grouperLogin(true), 
	lockPassword(true), 
	lockToken(true), 
	;

	
	private JnAsyncBusiness(boolean mustSave) {
		this.mustSave = mustSave;
	}

	private final boolean mustSave;
	
	public Class<?> validationClass() {
		Class<? extends JnAsyncBusiness> class1 = this.getClass();
		return class1;
	}
	
	public final boolean canSave() {
		return this.mustSave;
	}
}

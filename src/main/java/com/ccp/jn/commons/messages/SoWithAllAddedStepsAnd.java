package com.ccp.jn.commons.messages;

public class SoWithAllAddedStepsAnd {

	final JnCommonsSendMessage getMessage;

	SoWithAllAddedStepsAnd(JnCommonsSendMessage getMessage) {
		this.getMessage = getMessage;
	}
	
	public WithTheTemplateId withTheTemplateEntity(String templateId) {
		return new WithTheTemplateId(this, templateId);
	}
	
}

package com.ccp.jn.commons.messages;

public class AddDefaultStep {

	final JnCommonsSendMessage getMessage;

	AddDefaultStep(JnCommonsSendMessage getMessage) {
		this.getMessage = getMessage;
	}

	public CreateStep andCreateAnotherStep() {
		return new CreateStep(this.getMessage);
	}

	public SoWithAllAddedStepsAnd soWithAllAddedProcessAnd() {
		return new SoWithAllAddedStepsAnd(this.getMessage);
	}

	public JnCommonsSendMessage and() {
		return this.getMessage;
	}
}

package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.process.CcpNextStep;
import com.ccp.process.CcpStepResult;
import com.jn.commons.JnEntity;

public class EvaluateTries extends CcpNextStep {

	private final JnEntity entity;
	
	private final int regularFlow;
	
	private final int excedeedFlow;
	
	
	public EvaluateTries(JnEntity entity, Integer regularFlow, int exceededFlow) {
		this.excedeedFlow = exceededFlow;
		this.regularFlow = regularFlow;
		this.entity = entity;
	}

	@Override
	public CcpStepResult executeThisStep(CcpMapDecorator values) {
		
		boolean exceededTries = this.entity.exceededTries(values, "tries", 3);
		
		if(exceededTries) {
			return new CcpStepResult(values, this.excedeedFlow, this);
		}
		
		return new CcpStepResult(values, this.regularFlow, this);
	}

}

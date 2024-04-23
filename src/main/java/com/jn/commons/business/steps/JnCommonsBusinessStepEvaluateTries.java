package com.jn.commons.business.steps;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.process.CcpNextStep;
import com.ccp.process.CcpProcessStatus;
import com.ccp.process.CcpStepResult;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnCommonsBusinessStepEvaluateTries extends CcpNextStep {

	private final JnBaseEntity entity;
	
	private final int regularFlow;
	
	private final int excedeedFlow;
	
	
	public JnCommonsBusinessStepEvaluateTries(JnBaseEntity entity, CcpProcessStatus regularFlow, CcpProcessStatus exceededFlow) {
		this.excedeedFlow = exceededFlow.status();
		this.regularFlow = regularFlow.status();
		this.entity = entity;
	}

	@Override
	public CcpStepResult executeThisStep(CcpJsonRepresentation values) {
		
		boolean exceededTries = this.entity.exceededTries(values, "tries", 3);
		
		if(exceededTries) {
			return new CcpStepResult(values, this.excedeedFlow, this);
		}
		
		return new CcpStepResult(values, this.regularFlow, this);
	}

}

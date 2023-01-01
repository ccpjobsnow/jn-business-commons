package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.process.CcpNextStep;
import com.ccp.process.CcpStepResult;
import com.jn.commons.JnBusinessEntity;

public class EvaluateTries extends CcpNextStep {

	private final JnBusinessEntity table;
	
	private final int regularFlow;
	
	private final int excedeedFlow;
	
	
	public EvaluateTries(JnBusinessEntity table, int regularFlow, int exceededFlow) {
		this.excedeedFlow = exceededFlow;
		this.regularFlow = regularFlow;
		this.table = table;
	}

	@Override
	public CcpStepResult executeThisStep(CcpMapDecorator values) {
		
		boolean exceededTries = this.table.exceededTries(values, "tries", 3);
		
		if(exceededTries) {
			return new CcpStepResult(values, this.excedeedFlow, this);
		}
		
		return new CcpStepResult(values, this.regularFlow, this);
	}

}

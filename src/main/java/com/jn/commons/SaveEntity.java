package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.process.CcpNextStep;
import com.ccp.process.CcpStepResult;
import com.ccp.process.CcpProcessStatus;

public class SaveEntity extends CcpNextStep {

	private final Integer statusToReturnAfterSaving;

	private final JnEntity entity;
	
	
	SaveEntity(JnEntity entity, CcpProcessStatus statusToReturnAfterSaving) {
		this.statusToReturnAfterSaving = statusToReturnAfterSaving.status();
		this.entity = entity;
		this.addEmptyStep();
	}

	@Override
	public CcpStepResult executeThisStep(CcpMapDecorator values) {
		this.entity.createOrUpdate(values);
		return new CcpStepResult(values.put("entity", this.entity.name()), this.statusToReturnAfterSaving, this);
	}

}

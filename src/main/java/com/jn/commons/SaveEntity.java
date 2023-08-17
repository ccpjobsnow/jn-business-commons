package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.process.CcpNextStep;
import com.ccp.process.CcpStepResult;
import com.ccp.process.CcpProcessStatus;

public class SaveEntity extends CcpNextStep {

	private final JnEntity entity;
	
	private final Integer status;
	
	SaveEntity(JnEntity entity, CcpProcessStatus status) {
		this.entity = entity;
		this.status = status.status();
	}

	@Override
	public CcpStepResult executeThisStep(CcpMapDecorator values) {
		this.entity.createOrUpdate(values);
		return new CcpStepResult(values.put("entity", this.entity.name()), this.status, this);
	}

}

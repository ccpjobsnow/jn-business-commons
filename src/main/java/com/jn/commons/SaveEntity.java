package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.process.CcpNextStep;
import com.ccp.process.CcpStepResult;

public class SaveEntity extends CcpNextStep {

	private final JnBusinessEntity entity;
	
	private final Integer status;
	
	SaveEntity(JnBusinessEntity entity, Integer status) {
		this.entity = entity;
		this.status = status;
	}

	@Override
	public CcpStepResult executeThisStep(CcpMapDecorator values) {
		this.entity.save(values);
		return new CcpStepResult(values.put("entity", this.entity.name()), this.status, this);
	}

}

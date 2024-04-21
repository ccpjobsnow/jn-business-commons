package com.jn.commons.business;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.process.CcpNextStep;
import com.ccp.process.CcpProcessStatus;
import com.ccp.process.CcpStepResult;

public class JnCommonsBusinessDeleteEntity extends CcpNextStep {

	private final Integer statusToReturnAfterSaving;

	private final CcpEntity entity;
	
	
	public JnCommonsBusinessDeleteEntity(CcpEntity entity, CcpProcessStatus statusToReturnAfterSaving) {
		this.statusToReturnAfterSaving = statusToReturnAfterSaving.status();
		this.entity = entity;
	}

	@Override
	public CcpStepResult executeThisStep(CcpJsonRepresentation values) {
		this.entity.delete(values);
		return new CcpStepResult(values.put("entity", this.entity.getEntityName()), this.statusToReturnAfterSaving, this);
	}

}

package com.jn.commons.business.steps;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.process.CcpNextStep;
import com.ccp.process.CcpProcessStatus;
import com.ccp.process.CcpStepResult;

public class JnCommonsBusinessStepSaveEntity extends CcpNextStep {

	private final Integer statusToReturnAfterSaving;

	private final CcpEntity entity;
	
	
	public JnCommonsBusinessStepSaveEntity(CcpEntity entity, CcpProcessStatus statusToReturnAfterSaving) {
		this.statusToReturnAfterSaving = statusToReturnAfterSaving.status();
		this.entity = entity;
		this.addEmptyStep();
	}

	@Override
	public CcpStepResult executeThisStep(CcpJsonRepresentation values) {
		this.entity.createOrUpdate(values);
		return new CcpStepResult(values.put("entity", this.entity.getEntityName()), this.statusToReturnAfterSaving, this);
	}

}

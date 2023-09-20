package com.jn.commons.business;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.process.CcpNextStep;
import com.ccp.process.CcpProcessStatus;
import com.ccp.process.CcpStepResult;

public class JnCommonsBusinessSaveEntity extends CcpNextStep {

	private final Integer statusToReturnAfterSaving;

	private final CcpEntity entity;
	
	
	public JnCommonsBusinessSaveEntity(CcpEntity entity, CcpProcessStatus statusToReturnAfterSaving) {
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

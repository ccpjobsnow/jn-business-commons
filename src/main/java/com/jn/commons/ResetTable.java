package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.process.CcpNextStep;
import com.ccp.process.CcpStepResult;

public class ResetTable extends CcpNextStep{
	
	private final JnBusinessEntity entity;
	
	public ResetTable(JnBusinessEntity entity) {
		this.entity = entity;
	}

	public CcpStepResult executeThisStep(CcpMapDecorator values) {
		CcpMapDecorator tables = values.getInternalMap("_tables");

		this.entity.removeTries(values, "tries", 3);

		CcpMapDecorator removeKey = tables.removeKey(this.entity.name());
		CcpMapDecorator put = values.put("_tables", removeKey);
		return new CcpStepResult(put, 200, this);
	}

}

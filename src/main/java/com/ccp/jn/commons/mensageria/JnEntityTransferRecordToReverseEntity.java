package com.ccp.jn.commons.mensageria;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityTransferRecordToReverseEntity;
import com.jn.commons.utils.JnExecuteBulkOperation;

public class JnEntityTransferRecordToReverseEntity implements JnTopic {

	private final CcpEntity entity;

	public JnEntityTransferRecordToReverseEntity(CcpEntity entity) {
		this.entity = entity;
	}

	@SuppressWarnings("unchecked")
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		CcpEntityTransferRecordToReverseEntity transferRecordToReverseEntity = this.entity.getTransferRecordToReverseEntity();
		JnExecuteBulkOperation.INSTANCE.executeSelectUnionAllThenExecuteBulkOperation(json, transferRecordToReverseEntity);
		return json;
	}
	
	
	
	
}

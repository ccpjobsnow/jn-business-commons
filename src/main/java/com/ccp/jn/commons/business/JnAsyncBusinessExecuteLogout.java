package com.ccp.jn.commons.business;

import com.ccp.constantes.CcpOtherConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityTransferRecordToReverseEntity;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.jn.commons.entities.JnEntityLoginSessionConflict;
import com.jn.commons.entities.JnEntityLoginSessionValidation;
import com.jn.commons.utils.JnDeleteFromEntity;
import com.jn.commons.utils.JnExecuteBulkOperation;

public class JnAsyncBusinessExecuteLogout implements JnTopic{


	public static final JnAsyncBusinessExecuteLogout INSTANCE = new JnAsyncBusinessExecuteLogout();
	
	private JnAsyncBusinessExecuteLogout() {}
	
	@SuppressWarnings("unchecked")
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		
		CcpEntityTransferRecordToReverseEntity executeLogout = JnEntityLoginSessionValidation.ENTITY.getTransferRecordToReverseEntity();
		JnDeleteFromEntity deleteLoginSessionConflict = new JnDeleteFromEntity(JnEntityLoginSessionConflict.ENTITY);
		JnExecuteBulkOperation.INSTANCE.
		executeSelectUnionAllThenExecuteBulkOperation(
				json 
				, executeLogout
				, deleteLoginSessionConflict
				);
		
		return CcpOtherConstants.EMPTY_JSON;
	}

}

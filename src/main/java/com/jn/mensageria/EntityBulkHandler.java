package com.jn.mensageria;

import java.util.List;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.crud.CcpHandleWithSearchResultsInTheEntity;
import com.ccp.especifications.db.utils.CcpEntity;
import com.jn.db.bulk.JnExecuteBulkOperation;
import com.jn.entities.JnEntityAsyncTask;

public class EntityBulkHandler implements JnTopic {

	private final CcpEntity entity;
	
	public EntityBulkHandler(CcpEntity entity) {
		super();
		this.entity = entity;
	}

	@SuppressWarnings("unchecked")
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		String operation = json.getAsString(JnEntityAsyncTask.Fields.operation.name());
		JnBulkHandlers valueOf = JnBulkHandlers.valueOf(operation);
		CcpHandleWithSearchResultsInTheEntity<List<CcpBulkItem>> bulkHandler = valueOf.getBulkHandler(this.entity);
		JnExecuteBulkOperation.INSTANCE.executeSelectUnionAllThenExecuteBulkOperation(json, bulkHandler);
		return json;
	}

}

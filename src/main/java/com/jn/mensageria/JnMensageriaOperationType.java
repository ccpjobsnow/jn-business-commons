package com.jn.mensageria;

import com.ccp.especifications.db.utils.CcpEntity;

enum JnMensageriaOperationType {

	entityBulkHandler {
		JnTopic getTopicType(CcpEntity entity) {
			JnTopic result = new EntityBulkHandler(entity);
			return result;
		}
	},
	entityCrud {
		JnTopic getTopicType(CcpEntity entity) {
			JnTopic result = new EntityCrud(entity);
			return result;
		}
	},
	none{
		JnTopic getTopicType(CcpEntity entity) {
			throw new UnsupportedOperationException();
		}},
	;
	
	abstract JnTopic getTopicType(CcpEntity entity);
	
}

package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityDecorators;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.CcpEntityFactory;
import com.jn.commons.utils.JnEntityVersionable;

@CcpEntitySpecifications(cacheableEntity = true)
@CcpEntityDecorators(decorators = JnEntityVersionable.class)
public class JnEntityInstantMessengerParametersToSend implements CcpEntityConfigurator {
	
	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityInstantMessengerParametersToSend.class).entityInstance;

	public static enum Fields implements CcpEntityField{
		recipient(false), templateId(true), subjectType(false), moreParameters(false)
		;
		
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}

	}
//	public List<CcpBulkItem> getFirstRecordsToInsert() {
//		List<CcpBulkItem> createBulkItems = CcpEntityConfigurator.super.toCreateBulkItems(ENTITY, "{"
//				+ "	\"recipient\": 751717896,"
//				+ "	\"templateId\": \"notifyError\","
//				+ "	\"subjectType\": \"notifyError\","
//				+ "	\"moreParameters\":{"
//				+ "		\"maxTriesToSendMessage\": 10,"
//				+ "		\"sleepToSendMessage\":3000"
//				+ "	}"
//				+ "}");
//
//		return createBulkItems;
//	}

}

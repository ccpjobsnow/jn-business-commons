package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityAuditable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.jn.commons.utils.JnAuditableEntity;

@CcpEntityAuditable(auditableEntityFactory = JnAuditableEntity.class)
@CcpEntitySpecifications(cacheableEntity = true)
public class JnEntityInstantMessengerParametersToSend{
	
	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityInstantMessengerParametersToSend.class);

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
//		List<CcpBulkItem> createBulkItems = super.toCreateBulkItems("{"
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

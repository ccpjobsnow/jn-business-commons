package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityVersionable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityFactory;
import com.jn.commons.utils.JnEntityVersionable;

@CcpEntityVersionable(versionableEntityFactory = JnEntityVersionable.class)
@CcpEntitySpecifications(cacheableEntity = true, pathToFirstRecords = "algumacoisa")
public class JnEntityHttpApiParameters {

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityHttpApiParameters.class).entityInstance;
	
	public static enum Fields implements CcpEntityField{
		apiName(true), url(false), token (false), maxTries(false), sleep(false), method(false)
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
//				+ "	\"apiName\": \"email\","
//				+ "	\"url\": \"urlEmailKey\","
//				+ "	\"token\": \"tokenEmailKey\","
//				+ "	\"method\": \"POST\","
//				+ "	\"sleep\": 3000,"
//				+ "	\"maxTries\": 3"
//				+ "}", 
//				"{"
//				+ "	\"apiName\": \"instantMessenger\","
//				+ "	\"url\": \"urlInstantMessengerKey\","
//				+ "	\"token\": \"tokenInstantMessengerKey\","
//				+ "	\"method\": \"POST\","
//				+ "	\"sleep\": 3000,"
//				+ "	\"maxTries\": 3"
//				+ "}");
//
//		return createBulkItems;
//	}
}

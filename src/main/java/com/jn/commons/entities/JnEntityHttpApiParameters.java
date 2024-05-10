package com.jn.commons.entities;

import java.util.List;

import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityHttpApiParameters extends JnBaseEntity{

	public static final JnEntityHttpApiParameters INSTANCE = new JnEntityHttpApiParameters();
	
	private JnEntityHttpApiParameters() {
		super(Fields.values());
	}
	
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
	
	public List<CcpBulkItem> getFirstRecordsToInsert() {
		List<CcpBulkItem> createBulkItems = super.toCreateBulkItems("{"
				+ "	\"apiName\": \"email\","
				+ "	\"url\": \"urlEmailKey\","
				+ "	\"token\": \"tokenEmailKey\","
				+ "	\"method\": \"POST\","
				+ "	\"sleep\": 3000,"
				+ "	\"maxTries\": 3"
				+ "}", 
				"{"
				+ "	\"apiName\": \"instantMessenger\","
				+ "	\"url\": \"urlInstantMessengerKey\","
				+ "	\"token\": \"tokenInstantMessengerKey\","
				+ "	\"method\": \"POST\","
				+ "	\"sleep\": 3000,"
				+ "	\"maxTries\": 3"
				+ "}");

		return createBulkItems;
	}
}

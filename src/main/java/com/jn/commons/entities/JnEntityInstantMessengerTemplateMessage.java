package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityVersionable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.jn.commons.utils.JnEntityVersionable;

@CcpEntitySpecifications(cacheableEntity = true)
@CcpEntityVersionable(versionableEntityFactory = JnEntityVersionable.class)
public class JnEntityInstantMessengerTemplateMessage {

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityInstantMessengerTemplateMessage.class);

	public static enum Fields implements CcpEntityField{
		templateId(true),language(true), subject(false), message(false)
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
//				+ "	\"language\": \"portuguese\","
//				+ "	\"templateId\": \"notifyError\","
//				+ "	\"message\": \"{type}\\n\\n{stackTrace}\\n\\n\\n{msg}\\n\\nCaused by:\\n{cause}\""
//				+ "}");
//
//		return createBulkItems;
//	}

}

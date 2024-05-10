package com.jn.commons.entities;

import java.util.List;

import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityInstantMessengerTemplateMessage extends JnBaseEntity{

	public static final JnEntityInstantMessengerTemplateMessage INSTANCE = new JnEntityInstantMessengerTemplateMessage();

	private JnEntityInstantMessengerTemplateMessage() {
		super(Fields.values());
	}
	
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
	public List<CcpBulkItem> getFirstRecordsToInsert() {
		List<CcpBulkItem> createBulkItems = super.toCreateBulkItems("{"
				+ "	\"language\": \"portuguese\","
				+ "	\"templateId\": \"notifyError\","
				+ "	\"message\": \"{type}\\n\\n{stackTrace}\\n\\n\\n{msg}\\n\\nCaused by:\\n{cause}\""
				+ "}");

		return createBulkItems;
	}

}

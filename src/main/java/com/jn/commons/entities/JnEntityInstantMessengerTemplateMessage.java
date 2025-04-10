package com.jn.commons.entities;

import java.util.List;

import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityDecorators;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityFactory;
import com.ccp.jn.commons.business.JnAsyncBusinessNotifyError;
import com.jn.commons.utils.JnEntityVersionable;
import com.jn.commons.utils.JnLanguage;

@CcpEntityDecorators(decorators = JnEntityVersionable.class)
@CcpEntitySpecifications(cacheableEntity = true, stepsBeforeSaveEntity = {})
public class JnEntityInstantMessengerTemplateMessage  implements CcpEntityConfigurator {

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityInstantMessengerTemplateMessage.class).entityInstance;

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
		List<CcpBulkItem> createBulkItems = CcpEntityConfigurator.super.toCreateBulkItems(ENTITY, "{"
				+ "	\"language\": \""
				+ JnLanguage.portuguese.name()
				+ "\","
				+ "	\"templateId\": \""
				+ JnAsyncBusinessNotifyError.class.getName()
				+ "\","
				+ "	\"message\": \"{type}\\n\\nError Description:\n {msg}\\n\\n{stackTrace}\\n\\nCaused by:\\n{cause}\""
				+ "}");

		return createBulkItems;
	}

}

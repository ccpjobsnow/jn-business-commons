package com.jn.commons.entities;

import java.util.List;

import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.CcpEntityDecorators;
import com.ccp.especifications.db.utils.decorators.CcpEntityFactory;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.jn.commons.utils.JnAsyncBusiness;
import com.jn.commons.utils.JnEntityVersionable;

@CcpEntityDecorators(decorators = JnEntityVersionable.class)
@CcpEntitySpecifications(cacheableEntity = true, jsonTransformations = {})
public class JnEntityEmailParametersToSend  implements CcpEntityConfigurator{

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityEmailParametersToSend.class).entityInstance;

	public static enum Fields implements CcpEntityField {
		email(false), sender(false), templateId(true), subjectType(false), moreParameters(false);

		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}

		public boolean isPrimaryKey() {
			return this.primaryKey;
		}
	}

	public List<CcpBulkItem> getFirstRecordsToInsert() {
		List<CcpBulkItem> createBulkItems = CcpEntityConfigurator.super.toCreateBulkItems(ENTITY, 
				"{" + "	\"email\": \"devs.jobsnow@gmail.com\"," + "	\"sender\": \"devs.jobsnow@gmail.com\","
						+ "	\"subjectType\": \"notifyError\"," + "	\"templateId\": \""
						+ JnAsyncBusiness.notifyError.name()		
						+ "\"" + "}",
				"{" + "	\"sender\": \"devs.jobsnow@gmail.com\"," + "	\"subjectType\": \"sendUserToken\","
						+ "	\"templateId\": \""
						+ JnAsyncBusiness.sendUserToken.name()
						+ "\"," + "	\"moreParameters\": {"
						+ "		\"linkedinAddress\": \"https://www.linkedin.com/in/onias85/\","
						+ "		\"linkedinName\": \"Onias\","
						+ "		\"accessLink\": \"https://ccpjobsnow.com/#/tokenToSetPassword?email={email}&msgType=info&msgValue=newUser&token={token}\","
						+ "		\"telegramGroupLink\": \"https://t.me/joinchat/q_PRgF_18n00NjEx\","
						+ "		\"botAddress\": \"https://t.me/JnSuporteBot\"" + "	}" + "}");

		return createBulkItems;
	}
}

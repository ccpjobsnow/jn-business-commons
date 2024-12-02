package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityVersionable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.jn.commons.utils.JnEntityVersionable;

@CcpEntityVersionable(versionableEntityFactory = JnEntityVersionable.class)
@CcpEntitySpecifications(cacheableEntity = true)
public class JnEntityEmailParametersToSend {

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityEmailParametersToSend.class);

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

//	public List<CcpBulkItem> getFirstRecordsToInsert() {
//		List<CcpBulkItem> createBulkItems = super.toCreateBulkItems(
//				"{" + "	\"email\": \"devs.jobsnow@gmail.com\"," + "	\"sender\": \"devs.jobsnow@gmail.com\","
//						+ "	\"subjectType\": \"notifyError\"," + "	\"templateId\": \"notifyError\"" + "}",
//				"{" + "	\"sender\": \"devs.jobsnow@gmail.com\"," + "	\"subjectType\": \"sendUserToken\","
//						+ "	\"templateId\": \"sendUserToken\"," + "	\"moreParameters\": {"
//						+ "		\"linkedinAddress\": \"https://www.linkedin.com/in/onias85/\","
//						+ "		\"linkedinName\": \"Onias\","
//						+ "		\"accessLink\": \"https://ccpjobsnow.com/#/tokenToSetPassword?email={email}&msgType=info&msgValue=newUser&token={token}\","
//						+ "		\"telegramGroupLink\": \"https://t.me/joinchat/q_PRgF_18n00NjEx\","
//						+ "		\"botAddress\": \"https://t.me/JnSuporteBot\"" + "	}" + "}");
//
//		return createBulkItems;
//	}
}

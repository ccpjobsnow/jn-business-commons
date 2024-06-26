package com.jn.commons.entities;

import java.util.List;

import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnAuditableEntity;

public class JnEntityEmailParametersToSend extends JnAuditableEntity {

	public static final JnEntityEmailParametersToSend INSTANCE = new JnEntityEmailParametersToSend();

	private JnEntityEmailParametersToSend() {
		super(Fields.values());
	}

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
		List<CcpBulkItem> createBulkItems = super.toCreateBulkItems(
				"{" + "	\"email\": \"devs.jobsnow@gmail.com\"," + "	\"sender\": \"devs.jobsnow@gmail.com\","
						+ "	\"subjectType\": \"notifyError\"," + "	\"templateId\": \"notifyError\"" + "}",
				"{" + "	\"sender\": \"devs.jobsnow@gmail.com\"," + "	\"subjectType\": \"sendUserToken\","
						+ "	\"templateId\": \"sendUserToken\"," + "	\"moreParameters\": {"
						+ "		\"linkedinAddress\": \"https://www.linkedin.com/in/onias85/\","
						+ "		\"linkedinName\": \"Onias\","
						+ "		\"accessLink\": \"https://ccpjobsnow.com/#/tokenToSetPassword?email={email}&msgType=info&msgValue=newUser&token={token}\","
						+ "		\"telegramGroupLink\": \"https://t.me/joinchat/q_PRgF_18n00NjEx\","
						+ "		\"botAddress\": \"https://t.me/JnSuporteBot\"" + "	}" + "}");

		return createBulkItems;
	}
}

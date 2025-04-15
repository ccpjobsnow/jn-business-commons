package com.jn.entities;

import java.util.List;

import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityDecorators;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityOperationSpecification;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityTransferOperationEspecification;
import com.ccp.especifications.db.utils.decorators.configurations.CcpIgnoreFieldsValidation;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityFactory;
import com.jn.business.commons.JnBusinessNotifyError;
import com.jn.business.login.JnBusinessSendUserToken;
import com.jn.entities.decorators.JnEntityVersionable;

@CcpEntityDecorators(decorators = JnEntityVersionable.class)
@CcpEntitySpecifications(
		inactivate = @CcpEntityTransferOperationEspecification(whenRecordToTransferIsFound = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class), whenRecordToTransferIsNotFound = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class)),
		reactivate = @CcpEntityTransferOperationEspecification(whenRecordToTransferIsFound = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class), whenRecordToTransferIsNotFound = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class)),
		delete = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class),
	    save = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class),
		cacheableEntity = true
)
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
						+ JnBusinessNotifyError.class.getName()		
						+ "\"" + "}",
				"{" + "	\"sender\": \"devs.jobsnow@gmail.com\"," + "	\"subjectType\": \"sendUserToken\","
						+ "	\"templateId\": \""
						+ JnBusinessSendUserToken.class.getName()
						+ "\"," + "	\"moreParameters\": {"
						+ "		\"linkedinAddress\": \"https://www.linkedin.com/in/onias85/\","
						+ "		\"linkedinName\": \"Onias\","
						+ "		\"accessLink\": \"https://ccpjobsnow.com/#/tokenToSetPassword?email={email}&msgType=info&msgValue=newUser&token={token}\","
						+ "		\"telegramGroupLink\": \"https://t.me/joinchat/q_PRgF_18n00NjEx\","
						+ "		\"botAddress\": \"https://t.me/JnSuporteBot\"" + "	}" + "}");

		return createBulkItems;
	}
}

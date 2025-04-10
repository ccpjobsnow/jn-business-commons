package com.ccp.jn.commons.business;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.jn.commons.messages.JnCommonsSendMessage;
import com.jn.commons.entities.JnEntityJobsnowError;
import com.jn.commons.entities.JnEntityJobsnowPenddingError;
import com.jn.commons.exceptions.JnCommonsSupportLanguageIsMissing;

public class JnCommonsNotifySupport {
	

	public static final JnCommonsNotifySupport INSTANCE = new JnCommonsNotifySupport();
	
	private JnCommonsNotifySupport() {
		
	}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json, String topic, CcpEntity entityToSaveError, JnCommonsSendMessage sender) {
		String supportLanguage = new CcpStringDecorator("application_properties").propertiesFrom().environmentVariablesOrClassLoaderOrFile().getAsString("supportLanguage");
		
		boolean hasNotLanguage = supportLanguage.trim().isEmpty();
		
		if(hasNotLanguage) {
			throw new JnCommonsSupportLanguageIsMissing();
		}

		CcpJsonRepresentation duplicateValueFromField = json.renameField(JnEntityJobsnowError.Fields.message.name(), "msg");
		CcpJsonRepresentation result = sender
		.addDefaultProcessForEmailSending()
		.and()
		.addDefaultStepForTelegramSending()
		.soWithAllAddedProcessAnd()
		.withTheTemplateEntity(topic)
		.andWithTheEntityToBlockMessageResend(entityToSaveError)
		.andWithTheMessageValuesFromJson(duplicateValueFromField)
		.andWithTheSupportLanguage(supportLanguage)
		.sendAllMessages();
		
		JnEntityJobsnowPenddingError.ENTITY.createOrUpdate(result);
		

		return json;
	}

}

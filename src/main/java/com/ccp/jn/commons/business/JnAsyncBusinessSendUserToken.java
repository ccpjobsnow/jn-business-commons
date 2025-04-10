
package com.ccp.jn.commons.business;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.ccp.jn.commons.messages.JnCommonsSendMessage;
import com.jn.commons.entities.JnEntityEmailTemplateMessage;
import com.jn.commons.entities.JnEntityInstantMessengerMessageSent;
import com.jn.commons.entities.JnEntityInstantMessengerParametersToSend;
import com.jn.commons.entities.JnEntityLoginToken;

public class JnAsyncBusinessSendUserToken implements JnTopic{
	
	public static final JnAsyncBusinessSendUserToken INSTANCE = new JnAsyncBusinessSendUserToken();
	
	private JnAsyncBusinessSendUserToken() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		String language = json.getAsString(JnEntityEmailTemplateMessage.Fields.language.name());
		CcpJsonRepresentation jsonPiece = JnEntityLoginToken.ENTITY.getHandledJson(json);
		
		String topic = this.getClass().getName();
		JnCommonsSendMessage getMessage = new JnCommonsSendMessage();
		
		CcpJsonRepresentation request = json.getInnerJson("request");
		CcpJsonRepresentation duplicateValueFromField = request.putAll(jsonPiece)
				.duplicateValueFromField("originalEmail", JnEntityLoginToken.Fields.email.name(), 
						JnEntityInstantMessengerParametersToSend.Fields.recipient.name())
				.duplicateValueFromField("originalToken", JnEntityInstantMessengerMessageSent.Fields.token.name())
				;
		getMessage
		.addDefaultProcessForEmailSending()
		.soWithAllAddedProcessAnd()
		.withTheTemplateEntity(topic)
		.andWithTheEntityToBlockMessageResend(JnEntityLoginToken.ENTITY)
		.andWithTheMessageValuesFromJson(duplicateValueFromField)
		.andWithTheSupportLanguage(language)
		.sendAllMessages()
		;

		return json;
	}

}

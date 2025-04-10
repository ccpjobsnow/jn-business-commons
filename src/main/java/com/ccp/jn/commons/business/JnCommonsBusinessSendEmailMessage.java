package com.ccp.jn.commons.business;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.email.CcpEmailSender;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.jn.commons.entities.JnEntityEmailMessageSent;
import com.jn.commons.entities.JnEntityEmailParametersToSend;
import com.jn.commons.entities.JnEntityEmailReportedAsSpam;
import com.jn.commons.utils.JnDeleteKeysFromCache;


public class JnCommonsBusinessSendEmailMessage implements JnTopic{

	public static final JnCommonsBusinessSendEmailMessage INSTANCE = new JnCommonsBusinessSendEmailMessage();
	
	private JnCommonsBusinessSendEmailMessage() {	}

	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		
		CcpEmailSender emailSender = CcpDependencyInjection.getDependency(CcpEmailSender.class);
		
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		
		CcpSelectUnionAll unionAll = crud.unionAll(json, JnDeleteKeysFromCache.INSTANCE, JnEntityEmailMessageSent.ENTITY, JnEntityEmailReportedAsSpam.ENTITY);
		
		boolean emailAlreadySent = JnEntityEmailMessageSent.ENTITY.isPresentInThisUnionAll(unionAll, json);
		
		if(emailAlreadySent) {
			return json;
		}

		boolean emailReportedAsSpam = JnEntityEmailReportedAsSpam.ENTITY.isPresentInThisUnionAll(unionAll, json);
		
		if(emailReportedAsSpam) {
			return json;
		}
		
		JnCommonsSendHttpRequest.INSTANCE.execute(json, x -> emailSender.send(x),JnCommonsHttpRequestType.email, JnEntityEmailParametersToSend.Fields.subjectType.name());
		JnEntityEmailMessageSent.ENTITY.createOrUpdate(json);
		return json;
	}

}

package com.jn.commons.utils;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.decorators.CcpTimeDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.mensageria.sender.CcpMensageriaSender;
import com.jn.commons.entities.JnEntityAsyncTask;

public enum JnTopic{
	jnSendUserToken, jnRequestTokenAgain, jnRequestUnlockToken,  
	jnNotifyContactUs, jnNotifyError, jnSendEmail, jnSendInstantMessage, jnRemoveTries;
	
	final CcpMensageriaSender mensageriaSender = CcpDependencyInjection.hasDependency(CcpMensageriaSender.class) ? CcpDependencyInjection.getDependency(CcpMensageriaSender.class) : null;
	
	public CcpJsonRepresentation send(CcpJsonRepresentation values) {
		String token = new CcpStringDecorator(CcpConstants.CHARACTERS_TO_GENERATE_TOKEN).text().generateToken(20);
		CcpJsonRepresentation messageDetails = CcpConstants.EMPTY_JSON
				.put("id", token)
				.put("request", values)
				.put("topic", this.name())
				.put("started", System.currentTimeMillis())
				.put("data", new CcpTimeDecorator().getFormattedCurrentDateTime("dd/MM/yyyy HH:mm:ss"))
				;
		JnEntityAsyncTask asyncTask = new JnEntityAsyncTask();
		String asyncTaskId = asyncTask.getId(messageDetails);
		CcpJsonRepresentation messageSent = values.put("asyncTaskId", asyncTaskId);
		this.mensageriaSender.send(this, messageSent);
		asyncTask.createOrUpdate(messageDetails, asyncTaskId);
		return messageSent;
	}

	public CcpJsonRepresentation getTranslatedAsyncTaskResult(CcpJsonRepresentation asyncTask) {
		return asyncTask;
	}
}

package com.jn.commons;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpMapDecorator;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.decorators.CcpTimeDecorator;
import com.ccp.dependency.injection.CcpInstanceInjection;
import com.ccp.especifications.mensageria.sender.CcpMensageriaSender;

public enum JnTopic{
	sendUserToken, requestTokenAgain, requestUnlockToken, saveCandidateData, 
	notifyContactUs, notifyError, saveResumesQuery, sendEmail, sendInstantMessage, removeTries;
	
	final CcpMensageriaSender mensageriaSender = CcpInstanceInjection.hasInstance(CcpMensageriaSender.class) ? CcpInstanceInjection.getInstance(CcpMensageriaSender.class) : null;
	
	public CcpMapDecorator send(CcpMapDecorator values) {
		String token = new CcpStringDecorator(CcpConstants.CHARACTERS_TO_GENERATE_TOKEN).text().generateToken(20);
		CcpMapDecorator messageDetails = new CcpMapDecorator()
				.put("id", token)
				.put("request", values)
				.put("topic", this.name())
				.put("started", System.currentTimeMillis())
				.put("data", new CcpTimeDecorator().getFormattedCurrentDateTime("dd/MM/yyyy HH:mm:ss"))
				;
		String asyncTaskId = JnEntity.async_task.getId(messageDetails);
		CcpMapDecorator messageSent = values.put("asyncTaskId", asyncTaskId);
		this.mensageriaSender.send(this, messageSent);
		JnEntity.async_task.createOrUpdate(messageDetails, asyncTaskId);
		return messageSent;
	}

	public CcpMapDecorator getTranslatedAsyncTaskResult(CcpMapDecorator asyncTask) {
		return asyncTask;
	}
}

package com.ccp.jn.commons.mensageria;

import java.lang.reflect.Constructor;
import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.jn.commons.entities.JnEntityAsyncTask;
import com.jn.commons.exceptions.JnInvalidTopic;

public class JnMensageriaReceiver {
	
	private JnMensageriaReceiver() {}
	
	public static final JnMensageriaReceiver INSTANCE = new JnMensageriaReceiver();
	
	private JnMensageriaReceiver saveResult(
			CcpEntity entity, 
			CcpJsonRepresentation messageDetails, 
			Throwable e,
			Function<CcpJsonRepresentation, CcpJsonRepresentation> jnAsyncBusinessNotifyError
			) {
		CcpJsonRepresentation response = new CcpJsonRepresentation(e);
		JnMensageriaReceiver saveResult = this.saveResult(entity, messageDetails, response, false);
		return saveResult;
		
	}

	private JnMensageriaReceiver saveResult(CcpEntity entity, CcpJsonRepresentation messageDetails, CcpJsonRepresentation response) {
		JnMensageriaReceiver saveResult = this.saveResult(entity, messageDetails, response, true);
		return saveResult;
	}
	
	
	
	public JnMensageriaReceiver executeProcesss(
			CcpEntity entity,
			String processName, 
			CcpJsonRepresentation json,
			Function<CcpJsonRepresentation, CcpJsonRepresentation> jnAsyncBusinessNotifyError
			) {
		try {
			Function<CcpJsonRepresentation, CcpJsonRepresentation> process = this.getProcess(processName, json);
			CcpJsonRepresentation response = process.apply(json);
			JnMensageriaReceiver saveResult = this.saveResult(entity, json, response);
			return saveResult;
		} catch (Throwable e) {
			JnMensageriaReceiver saveResult = this.saveResult(entity, json, e, jnAsyncBusinessNotifyError);
			return saveResult;
		}
	}
	
	public JnTopic getProcess(String processName, CcpJsonRepresentation json){
		
		Object newInstance;

		try {
			Class<?> forName = Class.forName(processName);
			Constructor<?> constructor = forName.getConstructor();
			constructor.setAccessible(true);
			newInstance = constructor.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if(newInstance instanceof JnTopic topic) {
			return topic;
		}
		
		if(newInstance instanceof CcpEntity entity) {
			JnEntityTopic jnEntityTopic = new JnEntityTopic(entity, json);
			return jnEntityTopic;
		}
		
		throw new JnInvalidTopic(processName);
	}
	
	private JnMensageriaReceiver saveResult(CcpEntity entity, CcpJsonRepresentation messageDetails, CcpJsonRepresentation response, boolean success) {
		Long finished = System.currentTimeMillis();
		CcpJsonRepresentation oneById = entity.getOneById(messageDetails);
		Long started = oneById.getAsLongNumber(JnEntityAsyncTask.Fields.started.name());
		Long enlapsedTime = finished - started;
		CcpJsonRepresentation processResult = messageDetails
				.put(JnEntityAsyncTask.Fields.enlapsedTime.name(), enlapsedTime)
				.put(JnEntityAsyncTask.Fields.response.name(), response)
				.put(JnEntityAsyncTask.Fields.finished.name(), finished)
				.put(JnEntityAsyncTask.Fields.success.name(), success);
		entity.createOrUpdate(processResult);
		return this;
	}

}

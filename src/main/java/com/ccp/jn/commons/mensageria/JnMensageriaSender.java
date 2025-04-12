package com.ccp.jn.commons.mensageria;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ccp.constantes.CcpOtherConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpTimeDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityBulkOperationType;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityCrudOperationType;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityExpurgableOptions;
import com.ccp.especifications.mensageria.sender.CcpMensageriaSender;
import com.jn.commons.entities.JnEntityAsyncTask;
import com.jn.commons.utils.JnExecuteBulkOperation;

public class JnMensageriaSender implements Function<CcpJsonRepresentation, CcpJsonRepresentation> {
	
	private final CcpMensageriaSender mensageriaSender = CcpDependencyInjection.getDependency(CcpMensageriaSender.class);
	
	private final String operationType;
	private final String topic;
	
	public JnMensageriaSender(JnTopic topic) {
		this.topic = topic.getClass().getSimpleName();
		this.operationType = CcpEntityCrudOperationType.none.name();
	}

	public JnMensageriaSender(CcpEntity entity, CcpEntityCrudOperationType operation) {
		this.topic = entity.getClass().getSimpleName();
		this.operationType = operation.name();
	}

	public JnMensageriaSender(CcpEntity entity, Class<?> operation) {
		this.topic = entity.getClass().getSimpleName();
		this.operationType = operation.getName();
	}

	public Map<String, Object> apply(Map<String, Object> map) {
		CcpJsonRepresentation json = new CcpJsonRepresentation(map);
		CcpJsonRepresentation response = this.apply(json);
		return response.content;
	} 
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {

		CcpJsonRepresentation put = json.put(JnEntityAsyncTask.Fields.topic.name(), this.topic); 
		
		CcpJsonRepresentation messageDetails = this.getMessageDetails(put);
		
		JnEntityAsyncTask.ENTITY.createOrUpdate(messageDetails);
		
		this.mensageriaSender.send(this.topic, messageDetails);

		return messageDetails;
	}
	
	public String toString() {
		return this.topic.getClass().getName();
	}
	
	private CcpJsonRepresentation getMessageDetails(CcpJsonRepresentation json) {
		CcpTimeDecorator ccpTimeDecorator = new CcpTimeDecorator();
		String formattedCurrentDateTime = ccpTimeDecorator.getFormattedDateTime(CcpEntityExpurgableOptions.second.format);
		
		CcpJsonRepresentation messageDetails = CcpOtherConstants.EMPTY_JSON
				.put(JnEntityAsyncTask.Fields.started.name(), System.currentTimeMillis())
				.put(JnEntityAsyncTask.Fields.data.name(), formattedCurrentDateTime)
				.put(JnEntityAsyncTask.Fields.operation.name(), this.operationType)
				.put(JnEntityAsyncTask.Fields.messageId.name(), UUID.randomUUID())
				.put(JnEntityAsyncTask.Fields.topic.name(), this.topic)
				.put(JnEntityAsyncTask.Fields.request.name(), json)
				.putAll(json)
				;
		return messageDetails;
	}
	
	private CcpBulkItem toBulkItem(CcpEntity entity, CcpJsonRepresentation json) {
		String asyncTaskId = entity.calculateId(json);
		CcpBulkItem bulkItem = new CcpBulkItem(json, CcpEntityBulkOperationType.create, entity, asyncTaskId);
		return bulkItem;
	}
	
	private boolean canSave(CcpJsonRepresentation x) {
		JnTopic process = JnMensageriaReceiver.INSTANCE.getProcess(this.topic, x);
		boolean canSave = process.canSave();
		return canSave;
	}
	
	public JnMensageriaSender send(CcpEntity entity, CcpJsonRepresentation... messages) {
		List<CcpJsonRepresentation> msgs = Arrays.asList(messages).stream().map(json -> this.getMessageDetails(json)).collect(Collectors.toList());
		List<CcpBulkItem> bulkItems = msgs.stream().filter(x -> this.canSave(x)).map(msg -> this.toBulkItem(entity, msg)).collect(Collectors.toList());
		JnExecuteBulkOperation.INSTANCE.executeBulk(bulkItems);
		this.mensageriaSender.send(this.topic, msgs);
		return this;
	}
	
	public JnMensageriaSender send(List<CcpJsonRepresentation> messages) {
		
		int size = messages.size();
		CcpJsonRepresentation[] a = new CcpJsonRepresentation[size];
		CcpJsonRepresentation[] array = messages.toArray(a);
		JnMensageriaSender send = this.send(JnEntityAsyncTask.ENTITY, array);
		return send;
	}

	public CcpJsonRepresentation send(CcpJsonRepresentation... messages) {
		this.send(JnEntityAsyncTask.ENTITY, messages);
		return CcpOtherConstants.EMPTY_JSON;
	}

}

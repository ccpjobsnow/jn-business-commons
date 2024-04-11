package com.jn.commons.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.dao.CcpDao;
import com.ccp.especifications.db.utils.CcpEntity;

public class JnCommonsBusinessGetMessage {

	private final List<Function<CcpJsonRepresentation, CcpJsonRepresentation>> process = new ArrayList<>();

	private final List<CcpEntity> parameterEntities = new ArrayList<>() ;
	
	private final List<CcpEntity> messageEntities = new ArrayList<>();
	
	public JnCommonsBusinessGetMessage addOneStep(Function<CcpJsonRepresentation, CcpJsonRepresentation> process, CcpEntity parameterEntity, CcpEntity messageEntity) {
		
		JnCommonsBusinessGetMessage getMessage = new JnCommonsBusinessGetMessage();
		
		getMessage.parameterEntities.addAll(this.parameterEntities);
		getMessage.messageEntities.addAll(this.messageEntities);
		getMessage.process.addAll(this.process);
		
		getMessage.parameterEntities.add(parameterEntity);
		getMessage.messageEntities.add(messageEntity);
		getMessage.process.add(process);
//		getMessage.dao = this.dao;
		
		return getMessage;
	}
	
	public JnCommonsBusinessGetMessage addOneLenientStep(Function<CcpJsonRepresentation, CcpJsonRepresentation> step, CcpEntity parameterEntity, CcpEntity messageEntity) {
		JnCommonsBusinessLenientProcess lenientProcess = new JnCommonsBusinessLenientProcess(step);
		JnCommonsBusinessGetMessage addFlow = this.addOneStep(lenientProcess, parameterEntity, messageEntity);
		return addFlow;
	}	
	public CcpJsonRepresentation executeAllSteps(String entityId, CcpEntity entityToSave, CcpJsonRepresentation entityValues, String language) {
		
		List<CcpEntity> allEntitiesToSearch = new ArrayList<>();
		allEntitiesToSearch.addAll(this.parameterEntities);
		allEntitiesToSearch.addAll(this.messageEntities);
		allEntitiesToSearch.add(entityToSave);
		
		CcpEntity[] entities = allEntitiesToSearch.toArray(new CcpEntity[allEntitiesToSearch.size()]);
		CcpJsonRepresentation idToSearch = entityValues.put("language", language)
				.put("templateId", entityId);
		CcpDao dao = CcpDependencyInjection.getDependency(CcpDao.class);
		CcpJsonRepresentation allData = dao.getAllData(idToSearch, entities);
		boolean alreadySaved = allData.containsAllKeys(entityToSave.name());
		
		if(alreadySaved) {
			return entityValues;
		}
		int k = 0;
		
		for (CcpEntity messageEntity : this.messageEntities) {
			
			CcpEntity parameterEntity = this.parameterEntities.get(k);
			
			CcpJsonRepresentation parameterData = allData.getInnerJson(parameterEntity.name());
			CcpJsonRepresentation moreParameters = parameterData.getInnerJson("moreParameters");
			CcpJsonRepresentation allParameters = parameterData.removeKey("moreParameters").putAll(moreParameters);
			CcpJsonRepresentation messageData = allData.getInnerJson(messageEntity.name());
			
			CcpJsonRepresentation allDataTogether = messageData.putAll(allParameters).putAll(entityValues);
			
			Set<String> keySet = messageData.keySet();
			
			CcpJsonRepresentation messageToSend = allDataTogether;
			
			for (String key : keySet) {
				messageToSend = messageToSend.putFilledTemplate(key, key);
			}
			Function<CcpJsonRepresentation, CcpJsonRepresentation> process = this.process.get(k);
			process.apply(messageToSend);
			k++;
		}
		entityToSave.createOrUpdate(entityValues);
		return entityValues;
	}
}

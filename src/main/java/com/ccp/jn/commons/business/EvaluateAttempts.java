package com.ccp.jn.commons.business;

import java.util.function.Function;

import com.ccp.constantes.CcpOtherConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.password.CcpPasswordHandler;
import com.ccp.exceptions.process.CcpFlowDisturb;
import com.ccp.jn.commons.mensageria.JnMensageriaSender;
import com.ccp.jn.commons.mensageria.JnTopic;
import com.ccp.process.CcpProcessStatus;

public class EvaluateAttempts implements Function<CcpJsonRepresentation, CcpJsonRepresentation>{

	
	private final CcpEntity entityToGetTheSecret;
	
	private final CcpEntity entityToGetTheAttempts;

	private final String userFieldName;
	
	private final String databaseFieldName;

	private final CcpProcessStatus statusToReturnWhenWrongType;
	
	private final CcpProcessStatus statusToReturnWhenExceedAttempts;
	
	private final JnTopic topicToRegisterSuccess;

	private final JnTopic topicToCreateTheLockWhenExceedTries;
	
	private final String fieldAttempsName;
	
	private final String fieldEmailName;

	public EvaluateAttempts(
			CcpEntity entityToGetTheAttempts, 
			CcpEntity entityToGetTheSecret, 
			String databaseFieldName, 
			String userFieldName, 
			CcpProcessStatus statusToReturnWhenExceedAttempts, 
			CcpProcessStatus statusToReturnWhenWrongType,
			JnTopic topicToCreateTheLockWhenExceedTries,
			JnTopic topicToRegisterSuccess,
			String fieldAttempsName,
			String fieldEmailName
			) { 

		this.statusToReturnWhenExceedAttempts = statusToReturnWhenExceedAttempts;
		this.statusToReturnWhenWrongType = statusToReturnWhenWrongType;
		this.topicToRegisterSuccess = topicToRegisterSuccess;
		this.entityToGetTheAttempts = entityToGetTheAttempts;
		this.entityToGetTheSecret = entityToGetTheSecret;
		this.databaseFieldName = databaseFieldName;
		this.userFieldName = userFieldName;
		this.topicToCreateTheLockWhenExceedTries = topicToCreateTheLockWhenExceedTries;
		this.fieldAttempsName = fieldAttempsName;
		this.fieldEmailName = fieldEmailName;
				
	}

	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		
		String secretFromDatabase = json.getValueFromPath("","_entities", this.entityToGetTheSecret.getEntityName(), this.databaseFieldName);
		
		String secretFomUser = json.getAsString(this.userFieldName);
		
		CcpPasswordHandler dependency = CcpDependencyInjection.getDependency(CcpPasswordHandler.class);
		
		boolean correctSecret = dependency.matches(secretFomUser, secretFromDatabase);
		
		CcpJsonRepresentation toReturn = json.removeField("_entities");
		
		if(correctSecret) {

			new JnMensageriaSender(this.topicToRegisterSuccess).apply(toReturn);
			return toReturn;
		}

		String attemptsEntityName = this.entityToGetTheAttempts.getEntityName();
		Double attemptsFromDatabase = json.getValueFromPath(0d,"_entities", attemptsEntityName, this.fieldAttempsName);
		//LATER PARAMETRIZAR O 3
		boolean exceededAttempts = attemptsFromDatabase >= 3;
		if(exceededAttempts) {
			new JnMensageriaSender(this.topicToCreateTheLockWhenExceedTries).apply(toReturn);
			throw new CcpFlowDisturb(toReturn, this.statusToReturnWhenExceedAttempts);
		}
		
		String email = json.getAsString(this.fieldEmailName);
		CcpJsonRepresentation put = CcpOtherConstants.EMPTY_JSON
				.put(this.fieldAttempsName, attemptsFromDatabase + 1)
				.put(this.fieldEmailName, email)
				;
		this.entityToGetTheAttempts.createOrUpdate(put);
		throw new CcpFlowDisturb(toReturn, this.statusToReturnWhenWrongType);
	}
	
	
	
}

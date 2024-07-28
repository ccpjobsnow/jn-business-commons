package com.jn.commons.entities.base;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.exceptions.process.CcpFlow;
import com.ccp.process.CcpProcessStatus;

public abstract class JnMirrorEntity extends JnAuditableEntity {

	protected JnMirrorEntity(CcpEntityField[] fields) {
		super(fields);
	}

	private void validateMirrorEntity(CcpJsonRepresentation json) {
		CcpEntity mirrorEntity = this.getMirrorEntity();
		boolean doesNotExist = mirrorEntity.exists(json) == false;
		
		if(doesNotExist) {
			return;
		}
		String id = mirrorEntity.calculateId(json);
		String errorMessage = String.format("The id '%s' has been moved from '%s' to '%s' ", id, this, mirrorEntity);
		throw new CcpFlow(json, CcpProcessStatus.REDIRECT, errorMessage, new String[0]);
	}
	
	public final CcpJsonRepresentation getOneById(CcpJsonRepresentation json) {
		this.validateMirrorEntity(json);
		CcpJsonRepresentation oneById = super.getOneById(json);
		return oneById;
	}
	
	public final CcpJsonRepresentation getOneById(CcpJsonRepresentation json, Function<CcpJsonRepresentation, CcpJsonRepresentation> ifNotFound) {
		this.validateMirrorEntity(json);
		CcpJsonRepresentation oneById = super.getOneById(json, ifNotFound);
		return oneById;
	}
	
	public boolean delete(CcpJsonRepresentation json) {
		
		boolean delete = super.delete(json);
		CcpEntity mirrorEntity = this.getMirrorEntity();
		mirrorEntity.create(json);
		return delete;
	}

	public boolean delete(String id) {
		CcpJsonRepresentation oneById = super.getOneById(id);
		boolean delete = super.delete(id);
		CcpEntity mirrorEntity = this.getMirrorEntity();
		mirrorEntity.create(oneById);
		return delete;
	}
	
	public final boolean hasMirrorEntity() {
		return true;
	}
	public CcpJsonRepresentation getRequiredEntityRow(CcpSelectUnionAll unionAll, CcpJsonRepresentation json) {
		CcpEntity mirrorEntity = this.getMirrorEntity();
		
		boolean itIsInMirrorEntity = mirrorEntity.isPresentInThisUnionAll(unionAll, json);
		if(itIsInMirrorEntity) {
			CcpJsonRepresentation requiredEntityRow = mirrorEntity.getRequiredEntityRow(unionAll, json);
			return requiredEntityRow;
		}
		
		CcpJsonRepresentation requiredEntityRow = super.getRequiredEntityRow(unionAll, json);
		return requiredEntityRow;
	}

}

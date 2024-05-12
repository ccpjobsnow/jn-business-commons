package com.jn.commons.entities.base;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.exceptions.process.CcpFlow;

public abstract class JnMirrorEntity extends JnAuditableEntity {

	protected JnMirrorEntity(CcpEntityField[] fields) {
		super(fields);
	}
	
	public boolean create(CcpJsonRepresentation values) {

		return super.create(values);
	}

	private void validateMirrorEntity(CcpJsonRepresentation values) {
		CcpEntity mirrorEntity = this.getMirrorEntity();
		boolean doesNotExist = mirrorEntity.exists(values) == false;
		
		if(doesNotExist) {
			return;
		}
		String id = mirrorEntity.getId(values);
		String errorMessage = String.format("The id '%s' has been moved from '%s' to '%s' ", id, this, mirrorEntity);
		throw new CcpFlow(values, 301, errorMessage, new String[0]);
	}
	
	public final CcpJsonRepresentation getOneById(CcpJsonRepresentation data) {
		this.validateMirrorEntity(data);
		CcpJsonRepresentation oneById = super.getOneById(data);
		return oneById;
	}
	
	public final CcpJsonRepresentation getOneById(CcpJsonRepresentation data, Function<CcpJsonRepresentation, CcpJsonRepresentation> ifNotFound) {
		this.validateMirrorEntity(data);
		CcpJsonRepresentation oneById = super.getOneById(data, ifNotFound);
		return oneById;
	}
	
	public boolean delete(CcpJsonRepresentation values) {
		
		boolean delete = super.delete(values);
		CcpEntity mirrorEntity = this.getMirrorEntity();
		mirrorEntity.create(values);
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


}

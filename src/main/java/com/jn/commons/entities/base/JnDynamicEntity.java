package com.jn.commons.entities.base;

import com.ccp.especifications.db.utils.CcpEntity;

public final class JnDynamicEntity extends JnMirrorEntity {

	private final String entityName;
	private final CcpEntity parent;
	
	protected JnDynamicEntity(String entityName, CcpEntity parent) {
		super(parent.getFields());
		this.entityName = entityName;
		this.parent = parent;
	}

	public String getEntityName() {
		return this.entityName;
	}
	
	public CcpEntity getMirrorEntity() {
		return this.parent;
	}
}

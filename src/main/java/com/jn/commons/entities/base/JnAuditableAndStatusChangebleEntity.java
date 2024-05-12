package com.jn.commons.entities.base;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;

public abstract class JnAuditableAndStatusChangebleEntity extends JnMirrorEntity{
	
	private final String otherEntityName;
	
	protected JnAuditableAndStatusChangebleEntity(String otherEntityName, CcpEntityField[] fields) {
		super(fields);
		this.otherEntityName = otherEntityName;
	}
	
	public final CcpEntity getMirrorEntity() {
		JnDynamicEntity jnDynamicEntity = new JnDynamicEntity(this.otherEntityName, this);
		return jnDynamicEntity;
	}
	
}

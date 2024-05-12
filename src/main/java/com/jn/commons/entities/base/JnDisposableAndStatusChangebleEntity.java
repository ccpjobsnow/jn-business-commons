package com.jn.commons.entities.base;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;

public abstract class JnDisposableAndStatusChangebleEntity extends JnDisposableEntity{
	
	private final String otherEntityName;
	
	protected JnDisposableAndStatusChangebleEntity(String otherEntityName,JnDiposableRecordTimeExpiration timeOption, CcpEntityField[] fields) {
		super(timeOption, fields);
		this.otherEntityName = otherEntityName;
	}
	
	public final CcpEntity getMirrorEntity() {
		JnDynamicEntity jnDynamicEntity = new JnDynamicEntity(this.otherEntityName, this);
		return jnDynamicEntity;
	}
	
	public final boolean hasMirrorEntity() {
		return true;
	}

}

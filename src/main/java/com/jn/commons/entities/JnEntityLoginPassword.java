package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityAuditable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityTwin;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.jn.commons.utils.JnAuditableEntity;

@CcpEntityAuditable(auditableEntityFactory = JnAuditableEntity.class)
@CcpEntityTwin(twinEntityName = "login_password_locked")
@CcpEntitySpecifications(cacheableEntity = true)
public class JnEntityLoginPassword {
	
	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityLoginPassword.class);
	
	public static enum Fields implements CcpEntityField{
		email(true), password(false)
		;
		
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}

	}

}

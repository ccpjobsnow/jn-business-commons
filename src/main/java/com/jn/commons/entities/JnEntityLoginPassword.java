package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityVersionable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityTwin;
import com.ccp.especifications.db.utils.decorators.CcpEntityFactory;
import com.jn.commons.utils.JnEntityVersionable;

@CcpEntityVersionable(versionableEntityFactory = JnEntityVersionable.class)
@CcpEntityTwin(twinEntityName = "login_password_locked")
@CcpEntitySpecifications(cacheableEntity = true)
public class JnEntityLoginPassword {
	
	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityLoginPassword.class).entityInstance;
	
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

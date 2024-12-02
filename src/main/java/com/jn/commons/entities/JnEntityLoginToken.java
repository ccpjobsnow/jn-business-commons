package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityTwin;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurg;
import com.jn.commons.utils.JnEntityExpurgable;

@CcpEntitySpecifications(cacheableEntity = true)
@CcpEntityTwin(twinEntityName = "login_token_locked")
@CcpEntityExpurgable(expurgableEntityFactory = JnEntityExpurgable.class, expurgTime = CcpEntityExpurg.daily)
public class JnEntityLoginToken {
	
	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityLoginToken.class);

	public static enum Fields implements CcpEntityField{
		email(true), originalEmail(false), tokenHash(false),ip(true), coordinates(false), macAddress(false), userAgent(true)
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

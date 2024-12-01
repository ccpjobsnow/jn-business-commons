package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityCacheable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifcations;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgable;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.ccp.especifications.db.utils.decorators.CcpLongevityCache;
import com.ccp.especifications.db.utils.decorators.CcpLongevityEntity;
import com.ccp.especifications.db.utils.decorators.CcpEntityTwin;
import com.jn.commons.entities.base.JnDisposableEntity;

@CcpEntityExpurgable(expurgableEntityFactory = JnDisposableEntity.class, longevityEntity = CcpLongevityEntity.hourly)
@CcpEntityCacheable(cacheLongevity = CcpLongevityCache.HOUR)
@CcpEntityTwin(twinEntityName = "login_session_terminated")
@CcpEntitySpecifcations
public class JnEntityLoginSessionCurrent {

	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityLoginSessionCurrent.class);
	
	public static enum Fields implements CcpEntityField{
		email(true), sessionToken(false), ip(false), coordinates(false), macAddress(false), userAgent(false)
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

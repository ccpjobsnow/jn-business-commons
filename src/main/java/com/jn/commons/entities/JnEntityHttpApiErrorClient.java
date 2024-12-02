package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.ccp.especifications.db.utils.decorators.CcpLongevityEntity;
import com.jn.commons.utils.JnDisposableEntity;

@CcpEntityExpurgable(expurgableEntityFactory = JnDisposableEntity.class, longevityEntity = CcpLongevityEntity.hourly)
@CcpEntitySpecifications(cacheableEntity = true)
public class JnEntityHttpApiErrorClient {
	
	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityHttpApiErrorClient.class);

	public static enum Fields implements CcpEntityField{
		url(true), method(true), headers(true), request(false), apiName(true),details(true), response(false), status(false)
		,timestamp(false)
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

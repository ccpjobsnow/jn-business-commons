package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpFactoryEntity;
import com.ccp.especifications.db.utils.decorators.CcpLongevityEntity;
import com.jn.commons.utils.JnDisposableEntity;

@CcpEntityExpurgable(expurgableEntityFactory = JnDisposableEntity.class, longevityEntity = CcpLongevityEntity.daily)
@CcpEntitySpecifications(cacheableEntity = true)
public class JnEntityContactUsIgnored {
	
	public static final CcpEntity ENTITY = CcpFactoryEntity.getEntityInstance(JnEntityContactUsIgnored.class);
	
	public static enum Fields implements CcpEntityField{
		email(true)
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

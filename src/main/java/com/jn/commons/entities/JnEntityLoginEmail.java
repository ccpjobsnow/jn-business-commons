package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityFactory;
import com.jn.commons.json.transformers.JnJsonTransformerPutEmailHash;

@CcpEntitySpecifications(cacheableEntity = true, jsonTransformations = {JnJsonTransformerPutEmailHash.class})
public class JnEntityLoginEmail implements CcpEntityConfigurator {
	
	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityLoginEmail.class).entityInstance;

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

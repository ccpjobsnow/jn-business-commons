package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityFactory;
import com.jn.commons.json.transformers.JnJsonTransformerPutEmailHash;

@CcpEntitySpecifications(cacheableEntity = true, jsonTransformations = {JnJsonTransformerPutEmailHash.class})
public class JnEntityLoginConflict implements CcpEntityConfigurator {

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityLoginConflict.class).entityInstance;
	
	public static enum Fields implements CcpEntityField{
		email(true), oldLogin(false), newLogin(false)
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

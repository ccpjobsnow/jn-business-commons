package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityExpurgable;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityTwin;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityExpurgableOptions;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityFactory;
import com.jn.commons.json.transformers.JnJsonTransformerPutEmailHash;
import com.jn.commons.json.transformers.JnJsonTransformerPutPasswordSecret;
import com.jn.commons.utils.JnEntityExpurgable;

@CcpEntityTwin(twinEntityName = "login_password_locked")
@CcpEntityExpurgable(expurgTime = CcpEntityExpurgableOptions.monthly, expurgableEntityFactory = JnEntityExpurgable.class)
@CcpEntitySpecifications(cacheableEntity = true, jsonTransformations = {JnJsonTransformerPutEmailHash.class
		, JnJsonTransformerPutPasswordSecret.class
		})
public class JnEntityLoginPassword implements CcpEntityConfigurator {
	
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

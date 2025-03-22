package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgable;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgableOptions;
import com.ccp.especifications.db.utils.decorators.CcpEntityFactory;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityTwin;
import com.jn.commons.json.transformers.JnJsonTransformerPutEmailHash;
import com.jn.commons.json.transformers.JnJsonTransformerPutRandomTokenSecret;
import com.jn.commons.utils.JnEntityExpurgable;

@CcpEntityExpurgable(expurgTime = CcpEntityExpurgableOptions.hourly, expurgableEntityFactory = JnEntityExpurgable.class)
@CcpEntityTwin(twinEntityName = "login_session_terminated")
@CcpEntitySpecifications(cacheableEntity = true, jsonTransformations = {JnJsonTransformerPutEmailHash.class, JnJsonTransformerPutRandomTokenSecret.class})
public class JnEntityLoginSessionValidation implements CcpEntityConfigurator {

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityLoginSessionValidation.class).entityInstance;
	public static enum Fields implements CcpEntityField{
		email(true), token(true), ip(true), coordinates(false), macAddress(false), userAgent(true)
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

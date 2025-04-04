package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityDecorators;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityFactory;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityReadOnly;
import com.jn.commons.json.transformers.JnJsonTransformerPutFormattedCurrentDateAndCurrentTimeStamp;

@CcpEntityDecorators(decorators = CcpEntityReadOnly.class)
@CcpEntitySpecifications(cacheableEntity = true, jsonTransformations = {JnJsonTransformerPutFormattedCurrentDateAndCurrentTimeStamp.class})
public class JnEntityAudit implements CcpEntityConfigurator {

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityAudit.class).entityInstance;

	public static enum Fields implements CcpEntityField{
		timestamp(true), 
		operation(false),
		date(false),
		entity(true), 
		id(true),
		json(false)
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

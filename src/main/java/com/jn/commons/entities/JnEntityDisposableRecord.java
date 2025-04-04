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
@CcpEntitySpecifications(cacheableEntity = false, jsonTransformations = {JnJsonTransformerPutFormattedCurrentDateAndCurrentTimeStamp.class})
public class JnEntityDisposableRecord implements CcpEntityConfigurator {

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityDisposableRecord.class).entityInstance;

	public static enum Fields implements CcpEntityField{
		entity(true), 
		date(false),
		id(true), timestamp(false),json(false);
		
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}
	}
}

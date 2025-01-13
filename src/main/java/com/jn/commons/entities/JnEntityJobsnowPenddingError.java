package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.CcpEntityDecorators;
import com.ccp.especifications.db.utils.decorators.CcpEntityFactory;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityTwin;
import com.jn.commons.json.transformers.JnJsonTransformerPutFormattedCurrentDateAndCurrentTimeStamp;
import com.jn.commons.utils.JnEntityVersionable;

@CcpEntityTwin(twinEntityName = "jobsnow_solved_error")
@CcpEntityDecorators(decorators = JnEntityVersionable.class)
@CcpEntitySpecifications(cacheableEntity = true, jsonTransformations = {JnJsonTransformerPutFormattedCurrentDateAndCurrentTimeStamp.class})
public class JnEntityJobsnowPenddingError implements CcpEntityConfigurator {

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityJobsnowPenddingError.class).entityInstance;
	
	public static enum Fields implements CcpEntityField{
		cause(true), stackTrace(true), type(true), message(false), timestamp(false), date(false)
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

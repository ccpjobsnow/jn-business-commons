package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityExpurgable;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityValidation;
import com.ccp.especifications.db.utils.decorators.configurations.CcpNoValidation;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityExpurgableOptions;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityFactory;
import com.jn.commons.utils.JnEntityExpurgable;

@CcpEntityExpurgable(expurgTime = CcpEntityExpurgableOptions.hourly, expurgableEntityFactory = JnEntityExpurgable.class)
@CcpEntitySpecifications(
		changeStatus = @CcpEntityValidation(afterOperation = {}, beforeOperation = {}, jsonValidationClass = CcpNoValidation.class),
		delete = @CcpEntityValidation(afterOperation = {}, beforeOperation = {}, jsonValidationClass = CcpNoValidation.class),
	    save = @CcpEntityValidation(afterOperation = {}, beforeOperation = {}, jsonValidationClass = CcpNoValidation.class),
		cacheableEntity = true
)
public class JnEntityHttpApiErrorClient  implements CcpEntityConfigurator {
	
	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityHttpApiErrorClient.class).entityInstance;

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

package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgable;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityFactory;
import com.ccp.especifications.db.utils.decorators.CcpEntityExpurgableOptions;
import com.jn.commons.utils.JnEntityExpurgable;

@CcpEntityExpurgable(expurgableEntityFactory = JnEntityExpurgable.class, expurgTime = CcpEntityExpurgableOptions.hourly)
@CcpEntitySpecifications(cacheableEntity = true)
public class JnEntityJobsnowWarning {

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityJobsnowWarning.class).entityInstance;

	public static enum Fields implements CcpEntityField{
		cause(false), stackTrace(false), type(true), message(false)
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

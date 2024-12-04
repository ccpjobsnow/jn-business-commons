package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.CcpEntityFactory;

@CcpEntitySpecifications(cacheableEntity = true)
public class JnEntityLoginStats implements CcpEntityConfigurator {
	
	public static final CcpEntity INSTANCE = new CcpEntityFactory(JnEntityLoginStats.class).entityInstance;
	
	public static enum Fields implements CcpEntityField{
		email(true), balance(false), lastAccess(false), countAccess(false),
		openedTickets(false), closedTickets(false), balanceTransacionsCount(false)
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

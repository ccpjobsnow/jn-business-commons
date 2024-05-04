package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityLoginStats extends JnBaseEntity{
	
	public static final JnEntityLoginStats INSTANCE = new JnEntityLoginStats();
	
	private JnEntityLoginStats() {
		super(Fields.values());
	}
	
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

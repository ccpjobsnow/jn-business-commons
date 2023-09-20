package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityAudit extends JnBaseEntity{
	public JnEntityAudit() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		date(true), operation(false),
		entity(true), id(true),
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

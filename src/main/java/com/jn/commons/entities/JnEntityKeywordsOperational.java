package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityKeywordsOperational extends JnBaseEntity{
	public JnEntityKeywordsOperational() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		keyword(true)
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
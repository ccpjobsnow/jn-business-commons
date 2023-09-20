package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityKeywordsUnknow extends JnBaseEntity{
	public JnEntityKeywordsUnknow() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		keyword(true), jobType(true), keywordType(false)
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

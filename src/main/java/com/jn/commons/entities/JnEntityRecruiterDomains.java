package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityRecruiterDomains extends JnBaseEntity{
	public JnEntityRecruiterDomains() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		prefix(true), domain(false)
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

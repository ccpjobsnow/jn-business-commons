package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityDeniedViewToRecruiter extends JnBaseEntity{
	public JnEntityDeniedViewToRecruiter() {
		super(Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		recruiter(true), resume(true), domain(false)
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

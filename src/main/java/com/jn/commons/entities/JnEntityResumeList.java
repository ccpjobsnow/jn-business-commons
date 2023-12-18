package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;

public class JnEntityResumeList extends JnBaseEntity{
	public JnEntityResumeList() {
		super(CcpTimeOption.ddMMyyyyHH, Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		searchId(true), results(false), 
		total(false), from(false), size(false)
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

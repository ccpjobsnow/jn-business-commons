package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.utils.JnTimeOption;

public class JnEntitySearchResumeList extends JnBaseEntity{
	public JnEntitySearchResumeList() {
		super(JnTimeOption.ddMMyyyyHH, Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		requiredKeywords(true),
		optionalKeywords(true),
		recruiter(true),
		seniority(true),
		ddd(true),
		pcd(false),
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

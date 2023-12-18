package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;

public class JnEntityRecruiterViewResumes extends JnBaseEntity{
	public JnEntityRecruiterViewResumes() {
		super(CcpTimeOption.ddMMyyyy, Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		recruiter(true), resume(true)
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

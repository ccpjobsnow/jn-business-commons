package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;

public class JnEntityJobsnowError extends JnBaseEntity{
	public JnEntityJobsnowError() {
		super(CcpTimeOption.ddMMyyyyHH,Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		cause(false), stackTrace(false), type(true), message(false)
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

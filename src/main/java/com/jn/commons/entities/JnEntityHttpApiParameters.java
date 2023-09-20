package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityHttpApiParameters extends JnBaseEntity{
	public JnEntityHttpApiParameters() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		apiName(true), url(false), token (false), maxTries(false), sleep(false), method(false)
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

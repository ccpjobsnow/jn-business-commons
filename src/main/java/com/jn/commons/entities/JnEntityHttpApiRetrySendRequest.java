package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityHttpApiRetrySendRequest extends JnBaseEntity{
	public JnEntityHttpApiRetrySendRequest() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		url(true), method(true), headers(true), request(false), apiName(true), tries(true), response(false), status(false)
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

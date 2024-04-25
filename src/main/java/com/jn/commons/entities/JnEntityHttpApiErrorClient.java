package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityHttpApiErrorClient extends JnBaseEntity{
	
	public static final JnEntityHttpApiErrorClient INSTANCE = new JnEntityHttpApiErrorClient();

	private JnEntityHttpApiErrorClient() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		url(true), method(true), headers(true), request(false), apiName(true),details(true), response(false), status(false)
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

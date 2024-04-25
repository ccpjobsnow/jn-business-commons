package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityHttpApiErrorServer extends JnBaseEntity{
	
	public static final JnEntityHttpApiErrorServer INSTANCE = new JnEntityHttpApiErrorServer();

	
	private JnEntityHttpApiErrorServer() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		url(true), method(true), headers(true), request(false), apiName(true), response(false), status(false)
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

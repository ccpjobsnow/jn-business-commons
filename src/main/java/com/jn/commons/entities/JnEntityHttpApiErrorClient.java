package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnRecordStorageTimeExpiration;

public class JnEntityHttpApiErrorClient extends JnDisposableEntity{
	
	public static final JnEntityHttpApiErrorClient INSTANCE = new JnEntityHttpApiErrorClient();

	private JnEntityHttpApiErrorClient() {
		super(JnRecordStorageTimeExpiration.hourly, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		url(true), method(true), headers(true), request(false), apiName(true),details(true), response(false), status(false)
		,timestamp(false)
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

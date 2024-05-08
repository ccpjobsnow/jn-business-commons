package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnRecordStorageTimeExpiration;

public class JnEntityLoginSessionToken extends JnDisposableEntity{

	public static final JnEntityLoginSessionToken INSTANCE = new JnEntityLoginSessionToken();
	
	private JnEntityLoginSessionToken() {
		super(JnRecordStorageTimeExpiration.hourly, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), sessionToken(true), ip(true), coordinates(false), macAddress(false), userAgent(true)
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

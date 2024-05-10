package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnDiposableRecordTimeExpiration;

public class JnEntityLoginToken extends JnDisposableEntity{
	
	public static final JnEntityLoginToken INSTANCE = new JnEntityLoginToken();

	private JnEntityLoginToken() {
		super(JnDiposableRecordTimeExpiration.daily, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), tokenHash(false),ip(true), coordinates(false), macAddress(false), userAgent(true)
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

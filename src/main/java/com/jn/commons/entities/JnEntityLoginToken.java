package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDiposableRecordTimeExpiration;
import com.jn.commons.entities.base.JnDisposableAndStatusChangebleEntity;

public class JnEntityLoginToken extends JnDisposableAndStatusChangebleEntity{
	
	public static final JnEntityLoginToken INSTANCE = new JnEntityLoginToken();

	private JnEntityLoginToken() {
		super("login_token_locked", JnDiposableRecordTimeExpiration.daily, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), originalEmail(false), tokenHash(false),ip(true), coordinates(false), macAddress(false), userAgent(true)
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

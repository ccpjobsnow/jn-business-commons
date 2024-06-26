package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDiposableRecordTimeExpiration;
import com.jn.commons.entities.base.JnDisposableAndStatusChangebleEntity;

public class JnEntityLoginSessionCurrent extends JnDisposableAndStatusChangebleEntity{

	public static final JnEntityLoginSessionCurrent INSTANCE = new JnEntityLoginSessionCurrent();
	
	private JnEntityLoginSessionCurrent() {
		super("login_session_terminated", JnDiposableRecordTimeExpiration.hourly, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), sessionToken(false), ip(false), coordinates(false), macAddress(false), userAgent(false)
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

package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnDiposableRecordTimeExpiration;

public class JnEntityLoginSessionCurrent extends JnDisposableEntity{

	public static final JnEntityLoginSessionCurrent INSTANCE = new JnEntityLoginSessionCurrent();
	
	private JnEntityLoginSessionCurrent() {
		super(JnDiposableRecordTimeExpiration.hourly, Fields.values());
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

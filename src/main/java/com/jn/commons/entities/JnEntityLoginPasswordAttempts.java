package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnRecordStorageTimeExpiration;

public class JnEntityLoginPasswordAttempts extends JnDisposableEntity{

	public static final JnEntityLoginPasswordAttempts INSTANCE = new JnEntityLoginPasswordAttempts();
	
	private JnEntityLoginPasswordAttempts() {
		super(JnRecordStorageTimeExpiration.daily, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), attempts(false)
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

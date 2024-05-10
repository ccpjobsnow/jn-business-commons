package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnDiposableRecordTimeExpiration;

public class JnEntityLoginTokenAttempts extends JnDisposableEntity{

	public static final JnEntityLoginTokenAttempts INSTANCE = new JnEntityLoginTokenAttempts();

	
	private JnEntityLoginTokenAttempts() {
		super(JnDiposableRecordTimeExpiration.daily, Fields.values());
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

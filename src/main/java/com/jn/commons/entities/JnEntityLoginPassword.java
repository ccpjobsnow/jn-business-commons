package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnAuditableAndStatusChangebleEntity;

public class JnEntityLoginPassword extends JnAuditableAndStatusChangebleEntity{
	
	public static final JnEntityLoginPassword INSTANCE = new JnEntityLoginPassword();
	
	private JnEntityLoginPassword() {
		super("login_password_locked", Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), password(false)
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

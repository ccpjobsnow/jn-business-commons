package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnDiposableRecordTimeExpiration;

public class JnEntityEmailMessageSent extends JnDisposableEntity{
	
	public static final JnEntityEmailMessageSent INSTANCE = new JnEntityEmailMessageSent();

	private JnEntityEmailMessageSent() {
		super(JnDiposableRecordTimeExpiration.daily, Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		subject(false), subjectType(true), email(true), sender(false), message(false)
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

package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnRecordStorageTimeExpiration;

public class JnEntityContactUs extends JnDisposableEntity{

	public static final JnEntityContactUs INSTANCE = new JnEntityContactUs();
	
	private JnEntityContactUs() {
		super(JnRecordStorageTimeExpiration.daily, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		subjectType(true), email(true), subject(false), message(false), chatId(false), sender(false)
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

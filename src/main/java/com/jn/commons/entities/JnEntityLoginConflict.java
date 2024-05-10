package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnDiposableRecordTimeExpiration;

public class JnEntityLoginConflict extends JnDisposableEntity{

	public static final JnEntityLoginConflict INSTANCE = new JnEntityLoginConflict();

	private JnEntityLoginConflict() {
		super(JnDiposableRecordTimeExpiration.hourly, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		email(true), oldLogin(false), newLogin(false)
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

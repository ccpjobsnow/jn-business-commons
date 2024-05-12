package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnAuditableEntity;

public class JnEntityLoginConflict extends JnAuditableEntity{

	public static final JnEntityLoginConflict INSTANCE = new JnEntityLoginConflict();

	private JnEntityLoginConflict() {
		super(Fields.values());
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

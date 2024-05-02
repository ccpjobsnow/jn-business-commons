package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityLoginConflictSolved extends JnBaseEntity{

	public static final JnEntityLoginConflictSolved INSTANCE = new JnEntityLoginConflictSolved();
	
	private JnEntityLoginConflictSolved() {
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

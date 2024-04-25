package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityRequestTokenAgainResponsible extends JnBaseEntity{

	public static final JnEntityRequestTokenAgainResponsible INSTANCE = new JnEntityRequestTokenAgainResponsible();

	private JnEntityRequestTokenAgainResponsible() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		chatId(true)
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

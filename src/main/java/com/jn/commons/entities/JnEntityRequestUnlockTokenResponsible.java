package com.jn.commons.entities;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityRequestUnlockTokenResponsible extends JnBaseEntity{

	public static final JnEntityRequestUnlockTokenResponsible INSTANCE = new JnEntityRequestUnlockTokenResponsible();

	
	private JnEntityRequestUnlockTokenResponsible() {
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
	@Override
	public String getId(CcpJsonRepresentation values) {
		return super.getId(values);
	}
}

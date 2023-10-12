package com.jn.commons.entities;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityRequestUnlockTokenResponsible extends JnBaseEntity{
	public JnEntityRequestUnlockTokenResponsible() {
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
	public String getId(CcpMapDecorator values) {
		return super.getId(values);
	}
}

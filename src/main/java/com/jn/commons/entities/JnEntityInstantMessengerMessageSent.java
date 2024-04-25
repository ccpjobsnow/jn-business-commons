package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityInstantMessengerMessageSent extends JnBaseEntity{
	
	public static final JnEntityInstantMessengerMessageSent INSTANCE = new JnEntityInstantMessengerMessageSent();

	
	private JnEntityInstantMessengerMessageSent() {
		super(CcpTimeOption.ddMMyyyyHHmmss, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		token(true), recipient(true), subjectType(false), message(false), interval(true)
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

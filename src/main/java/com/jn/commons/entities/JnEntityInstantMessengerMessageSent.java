package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.utils.JnTimeOption;

public class JnEntityInstantMessengerMessageSent extends JnBaseEntity{
	public JnEntityInstantMessengerMessageSent() {
		super(JnTimeOption.ddMMyyyyHHmmss, Fields.values());
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

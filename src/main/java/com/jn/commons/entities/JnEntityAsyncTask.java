package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;

public class JnEntityAsyncTask extends JnBaseEntity{
	public JnEntityAsyncTask() {
		super(false, CcpTimeOption.ddMMyyyyHHmmssSSS, Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		started(false), finished(false), topic(false), request(false), id(true), success(false), response(false)
		;
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}

		@Override
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}

	}
}

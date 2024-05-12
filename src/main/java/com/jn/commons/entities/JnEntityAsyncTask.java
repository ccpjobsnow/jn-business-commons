package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnAuditableEntity;

public class JnEntityAsyncTask extends JnAuditableEntity{

	public static final JnEntityAsyncTask INSTANCE = new JnEntityAsyncTask();

	private JnEntityAsyncTask() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		started(false), finished(false), enlapsedTime(false), topic(false), request(false), messageId(true), success(false), response(false)
		;
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}

		
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}
	}
	
	public boolean canSaveCopy() {
		return false;
	}
}

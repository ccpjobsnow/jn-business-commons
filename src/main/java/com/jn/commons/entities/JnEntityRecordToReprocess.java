package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnAuditableEntity;

public class JnEntityRecordToReprocess extends JnAuditableEntity{
	
	public static final JnEntityRecordToReprocess INSTANCE = new JnEntityRecordToReprocess();

	
	private JnEntityRecordToReprocess() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		timestamp(true), operation(false),
		entity(true), id(true),
		json(false), status(false),
		reason(false), errorType(false)
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

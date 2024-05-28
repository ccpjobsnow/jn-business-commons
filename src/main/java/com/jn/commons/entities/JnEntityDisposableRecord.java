package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnAuditableEntity;

public class JnEntityDisposableRecord extends JnAuditableEntity{

	public static final JnEntityDisposableRecord INSTANCE = new JnEntityDisposableRecord();

	private JnEntityDisposableRecord() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		entity(true), 
		date(false),
		id(true), timestamp(false),json(false);
		
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

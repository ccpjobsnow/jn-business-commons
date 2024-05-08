package com.jn.commons.entities;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityCopy extends JnBaseEntity{

	public static final JnEntityCopy INSTANCE = new JnEntityCopy();

	private JnEntityCopy() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		entity(true), id(true), timestamp(false),json(false);
		
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}
	}
	public CcpBulkItem getRecordToBulkOperation(CcpJsonRepresentation values, CcpEntityOperationType operation) {
		throw new UnsupportedOperationException();
	}
}

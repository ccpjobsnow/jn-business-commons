package com.jn.commons.entities;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;

public class JnEntityAudit extends JnBaseEntity{

	public static final JnEntityAudit INSTANCE = new JnEntityAudit();

	private JnEntityAudit() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		date(true), operation(false),
		entity(true), id(true),
		json(false)
		;
		
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

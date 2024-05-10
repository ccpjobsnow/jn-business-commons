package com.jn.commons.entities;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnBaseEntity;
import com.jn.commons.entities.base.JnIncopiableEntity;

@JnIncopiableEntity
public class JnEntityDisposableRecords extends JnBaseEntity{

	public static final JnEntityDisposableRecords INSTANCE = new JnEntityDisposableRecords();

	private JnEntityDisposableRecords() {
		super(Fields.values());
	}
	
	protected CcpJsonRepresentation getAuditRecord(CcpJsonRepresentation values, CcpEntityOperationType operation) {
		throw new UnsupportedOperationException();
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
}

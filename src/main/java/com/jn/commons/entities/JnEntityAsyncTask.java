package com.jn.commons.entities;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnAuditableEntity;
import com.jn.commons.entities.base.JnIncopiableEntity;

@JnIncopiableEntity
public class JnEntityAsyncTask extends JnAuditableEntity{

	public static final JnEntityAsyncTask INSTANCE = new JnEntityAsyncTask();

	private JnEntityAsyncTask() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		started(false), finished(false), topic(false), request(false), id(true), success(false), response(false)
		;
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}

		
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}
	}
	protected void saveAuditory(CcpJsonRepresentation values, CcpEntityOperationType operation) {}
}

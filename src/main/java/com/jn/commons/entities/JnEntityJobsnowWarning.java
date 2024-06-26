package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnDiposableRecordTimeExpiration;

public class JnEntityJobsnowWarning extends JnDisposableEntity{

	public static final JnEntityJobsnowWarning INSTANCE = new JnEntityJobsnowWarning();

	private JnEntityJobsnowWarning() {
		super(JnDiposableRecordTimeExpiration.hourly, Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		cause(false), stackTrace(false), type(true), message(false)
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

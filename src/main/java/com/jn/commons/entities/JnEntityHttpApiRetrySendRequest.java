package com.jn.commons.entities;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.jn.commons.entities.base.JnDisposableEntity;
import com.jn.commons.entities.base.JnRecordStorageTimeExpiration;

public class JnEntityHttpApiRetrySendRequest extends JnDisposableEntity{

	public static final JnEntityHttpApiRetrySendRequest INSTANCE = new JnEntityHttpApiRetrySendRequest();
	
	private JnEntityHttpApiRetrySendRequest() {
		super(JnRecordStorageTimeExpiration.hourly, Fields.values());
	}
	public boolean exceededTries(CcpJsonRepresentation values, String fieldName, int limit) {
		
		for(int k = 1; k <= limit; k++) {
			
			CcpJsonRepresentation put = values.put(fieldName, k);
			
			boolean exists = this.exists(put);
			
			if(exists == false) {
				this.createOrUpdate(put);
				return false;
			}
		}
		return true;
	}

	public static enum Fields implements CcpEntityField{
		url(true), method(true), headers(true), request(false), apiName(true), tries(true), response(false), status(false)
		,timestamp(false), details(false)
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

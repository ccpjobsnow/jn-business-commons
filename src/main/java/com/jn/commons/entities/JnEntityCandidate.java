package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntityField;

public class JnEntityCandidate extends JnBaseEntity{
	public JnEntityCandidate() {
		super(Fields.values());
	}
	
	public static enum Fields implements CcpEntityField{
		pj(false), 
		ddd(false),
		pcd(false), 
		clt(false), 
		btc(false),
		email(true), 
		resume(false), 
		jobType(false),
		keywords(false),
		synonyms(false), 
		seniority(false), 
		desiredJob(false), 
		experience(false), 
		currentJob(false), 
		resumeWords(false),
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

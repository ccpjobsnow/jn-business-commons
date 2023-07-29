package com.jn.commons.entities.fields;

import com.ccp.especifications.db.utils.CcpField;

public enum A3D_candidate implements CcpField{
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

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	private A3D_candidate(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	
}

package com.jn.commons;

public enum JnMainSiteEndpoints {
	downloadResume(new String[] {}, new String[] {"viewMode", "recruiter", "professional", "hash", "fileType", "resume"}) 
	;
	
	public final String[] responseFields;
	public final String[] requestFields;

	private JnMainSiteEndpoints(String[] requestFields, String[] responseFields) {
		this.responseFields = responseFields;
		this.requestFields = requestFields;
	}
	
	
}

package com.jn.commons.status;

import com.ccp.process.CcpProcessStatus;

public enum StatusExecuteLogin implements CcpProcessStatus{
	invalidEmail(400),
	lockedToken(403),
	missingEmail(404),
	lockedPassword(423),
	wrongPassword(421),
	passwordLockedRecently(429),
	loginConflict(409),
	missingPassword(202),
	expectedStatus(200),
	invalidSession(401),
	weakPassword(422)
	;

	public final int status;
	
	
	
	private StatusExecuteLogin(int status) {
		this.status = status;
	}

	public int status() {
		return status;
	}
}

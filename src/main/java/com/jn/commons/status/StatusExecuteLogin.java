package com.jn.commons.status;

import com.ccp.process.CcpProcessStatus;

public enum StatusExecuteLogin implements CcpProcessStatus{
	passwordLockedRecently(429),
	missingSessionToken(401),
	missingSavePassword(202),
	lockedPassword(423),
	expectedStatus(200),
	invalidSession(401),
	wrongPassword(421),
	loginConflict(409),
	invalidEmail(400),
	missingSaveEmail(404),
	weakPassword(422),
	lockedToken(403),
	;

	public final int status;
	
	
	
	private StatusExecuteLogin(int status) {
		this.status = status;
	}

	public int asNumber() {
		return status;
	}
}

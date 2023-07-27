package com.jn.commons;

import com.ccp.especifications.mensageria.sender.CcpMensageriaTopic;

public enum JnTopic implements CcpMensageriaTopic{
	sendUserToken, requestTokenAgain, requestUnlockToken, saveCandidateData, 
	notifyContactUs, saveResumesQuery, sendEmail, sendInstantMessage, notifyError, removeTries
}

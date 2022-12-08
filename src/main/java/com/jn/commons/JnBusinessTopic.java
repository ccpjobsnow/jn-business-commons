package com.jn.commons;

import com.ccp.especifications.mensageria.sender.CcpMensageriaTopic;

public enum JnBusinessTopic implements CcpMensageriaTopic{
	sendUserToken, requestTokenAgain, requestUnlockToken, saveCandidateData, notifyContactUs, saveResumesQuery
}

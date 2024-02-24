package com.jn.commons.utils;

import com.ccp.especifications.mensageria.sender.CcpTopic;

public enum JnTopics implements CcpTopic {
	requestTokenAgain{

		public String getTopicName() {
			return null;
		}
		
	},
	requestUnlockToken{

		public String getTopicName() {
			return null;
		}
	}, sendUserToken {
		public String getTopicName() {
			return null;
		}
	}, removeTries {
		public String getTopicName() {
			return null;
		}
	}, notifyError {
		public String getTopicName() {
			return null;
		}
	}, notifyContactUs {
		public String getTopicName() {
			return null;
		}
	},
	;
}

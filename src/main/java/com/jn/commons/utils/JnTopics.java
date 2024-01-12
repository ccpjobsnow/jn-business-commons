package com.jn.commons.utils;

public enum JnTopics {
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
	public abstract String getTopicName();
}

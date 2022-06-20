package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.dependency.injection.CcpEspecification;
import com.ccp.especifications.mensageria.sender.CcpMensageriaSender;

public enum JnTopic {
	SAVE_RESUME_VIEW("viewMode", "recruiter", "professional", "hash", "fileType") {
		@Override
		CcpMapDecorator transformData(CcpMapDecorator data) {
			CcpMapDecorator subMap = data.getSubMap(this.fields);
			return subMap;
		}
	};

	public String[] fields;

	private JnTopic(String... fields) {
		this.fields = fields;
	}

	@CcpEspecification
	private CcpMensageriaSender mensageriaSender;

	public void sendToTopic(CcpMapDecorator data) {
		CcpMapDecorator transformData = this.transformData(data);
		this.mensageriaSender.send(transformData, this.name());

	}

	abstract CcpMapDecorator transformData(CcpMapDecorator data);
}

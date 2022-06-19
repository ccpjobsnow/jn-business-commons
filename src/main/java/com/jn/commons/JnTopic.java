package com.jn.commons;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.dependency.injection.CcpEspecification;
import com.ccp.especifications.mensageria.sender.CcpMensageriaSender;

public enum JnTopic {
	SAVE_RESUME_VIEW {
		@Override
		CcpMapDecorator transformData(CcpMapDecorator data) {
			return data.getSubMap("recruiter","professional","resume", "modeView");
		}
	}
;
	@CcpEspecification
	private CcpMensageriaSender mensageriaSender;
	
	
	
	public void sendToTopic(CcpMapDecorator data) {
		CcpMapDecorator transformData = this.transformData(data);
		this.mensageriaSender.send(transformData, this.name());
		
	}
	abstract CcpMapDecorator transformData(CcpMapDecorator data);
}

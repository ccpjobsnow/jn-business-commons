package com.ccp.jn.commons.messages;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.jn.commons.business.JnAsyncBusinessNotifyError;
import com.ccp.jn.commons.business.JnCommonsNotifySupport;
import com.jn.commons.entities.JnEntityJobsnowWarning;

public class JnCommonsSendMessageIgnoringProcessErrors extends JnCommonsSendMessage{

	
	public JnCommonsSendMessage addOneStep(Function<CcpJsonRepresentation, CcpJsonRepresentation> step, CcpEntity parameterEntity, CcpEntity messageEntity) {
		Function<CcpJsonRepresentation, CcpJsonRepresentation> lenientProcess = values -> {
			try {
				CcpJsonRepresentation apply = step.apply(values);
				return apply;
			} catch (Exception e) {
				CcpJsonRepresentation errorDetails = new CcpJsonRepresentation(e);
				String name = JnAsyncBusinessNotifyError.class.getName();
				JnCommonsSendMessage x = new JnCommonsSendMessageAndJustErrors();
				JnCommonsNotifySupport.INSTANCE.apply(errorDetails, name, JnEntityJobsnowWarning.ENTITY, x);
				return values;
			}
		};
		JnCommonsSendMessage addFlow = super.addOneStep(lenientProcess, parameterEntity, messageEntity);
		return addFlow;
	}	
}

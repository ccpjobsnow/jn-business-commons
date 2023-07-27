package com.jn.commons;

import java.util.List;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.especifications.db.bulk.CcpBulkAudit;

public class JnBulkAudit implements CcpBulkAudit {

	@Override
	public CcpMapDecorator commit(List<CcpMapDecorator> records, CcpMapDecorator bulkResult) {
		//TODO
		return new CcpMapDecorator();
	}

}

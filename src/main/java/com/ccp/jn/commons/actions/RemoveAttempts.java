package com.ccp.jn.commons.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityBulkOperationType;
import com.ccp.especifications.db.crud.CcpHandleWithSearchResultsInTheEntity;
import com.ccp.especifications.db.utils.CcpEntity;

public class RemoveAttempts implements CcpHandleWithSearchResultsInTheEntity<List<CcpBulkItem>>{
	
	private final CcpEntity entityAttempts;

	public RemoveAttempts(CcpEntity entityAttempts) {
		this.entityAttempts = entityAttempts;
	}

	public List<CcpBulkItem> whenRecordWasFoundInTheEntitySearch(CcpJsonRepresentation json, CcpJsonRepresentation recordFound) {
	
		CcpBulkItem attempts = this.entityAttempts.getMainBulkItem(json, CcpEntityBulkOperationType.delete);
		List<CcpBulkItem> asList = Arrays.asList(attempts);
		return asList;
	}

	public List<CcpBulkItem> whenRecordWasNotFoundInTheEntitySearch(CcpJsonRepresentation json) {
		return new ArrayList<>();
	}

	public CcpEntity getEntityToSearch() {
		return this.entityAttempts;
	}

}

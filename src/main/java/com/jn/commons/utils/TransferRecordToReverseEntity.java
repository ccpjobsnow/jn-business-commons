package com.jn.commons.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityBulkOperationType;
import com.ccp.especifications.db.crud.CcpHandleWithSearchResultsInTheEntity;
import com.ccp.especifications.db.utils.CcpEntity;

public class TransferRecordToReverseEntity implements CcpHandleWithSearchResultsInTheEntity<List<CcpBulkItem>>{

	private final CcpEntity from; 
	private final Function<CcpJsonRepresentation, CcpJsonRepresentation> doAfterSavingIfRecordIsFound;
	private final Function<CcpJsonRepresentation, CcpJsonRepresentation> doAfterSavingIfRecordIsNotFound;
	private final Function<CcpJsonRepresentation, CcpJsonRepresentation> doBeforeSavingIfRecordIsFound;
	private final Function<CcpJsonRepresentation, CcpJsonRepresentation> doBeforeSavingIfRecordIsNotFound;
	

	

	public TransferRecordToReverseEntity(CcpEntity from,
			Function<CcpJsonRepresentation, CcpJsonRepresentation> doAfterSavingIfRecordIsFound,
			Function<CcpJsonRepresentation, CcpJsonRepresentation> doAfterSavingIfRecordIsNotFound,
			Function<CcpJsonRepresentation, CcpJsonRepresentation> doBeforeSavingIfRecordIsFound,
			Function<CcpJsonRepresentation, CcpJsonRepresentation> doBeforeSavingIfRecordIsNotFound) {
		super();
		this.from = from;
		this.doAfterSavingIfRecordIsFound = doAfterSavingIfRecordIsFound;
		this.doAfterSavingIfRecordIsNotFound = doAfterSavingIfRecordIsNotFound;
		this.doBeforeSavingIfRecordIsFound = doBeforeSavingIfRecordIsFound;
		this.doBeforeSavingIfRecordIsNotFound = doBeforeSavingIfRecordIsNotFound;
	}

	public List<CcpBulkItem> whenRecordWasFoundInTheEntitySearch(CcpJsonRepresentation json, CcpJsonRepresentation recordFound) {
	
		CcpEntity twinEntity = this.from.getTwinEntity();
		CcpBulkItem itemTo = twinEntity.getMainBulkItem(json, CcpEntityBulkOperationType.create);
		CcpBulkItem itemFrom = this.from.getMainBulkItem(json, CcpEntityBulkOperationType.delete);
		List<CcpBulkItem> asList = Arrays.asList(itemTo, itemFrom);
		
		return asList;
	}

	//DOUBT DÁ PRA ESVAZIAR ESSA LISTA SEM CAUSAR REPERCUÇÕE???
	//LATER NO CASO DO LOGOUT FAZER UM TESTE DE TOKEN NAO ENCONTRADO
	public List<CcpBulkItem> whenRecordWasNotFoundInTheEntitySearch(CcpJsonRepresentation json) {
		CcpEntity twinEntity = this.from.getTwinEntity();
		CcpBulkItem itemTo = twinEntity.getMainBulkItem(json, CcpEntityBulkOperationType.create);
		List<CcpBulkItem> asList = Arrays.asList(itemTo);
		return asList;
	}

	public CcpEntity getEntityToSearch() {
		return this.from;
	}

	public Function<CcpJsonRepresentation, CcpJsonRepresentation> doAfterSavingIfRecordIsFound() {
		return this.doAfterSavingIfRecordIsFound;
	}

	public Function<CcpJsonRepresentation, CcpJsonRepresentation> doAfterSavingIfRecordIsNotFound() {
		return this.doAfterSavingIfRecordIsNotFound;
	}
	
	public Function<CcpJsonRepresentation, CcpJsonRepresentation> doBeforeSavingIfRecordIsFound() {
		return this.doBeforeSavingIfRecordIsFound;
	}

	public Function<CcpJsonRepresentation, CcpJsonRepresentation> doBeforeSavingIfRecordIsNotFound() {
		return this.doBeforeSavingIfRecordIsNotFound;
	}
}

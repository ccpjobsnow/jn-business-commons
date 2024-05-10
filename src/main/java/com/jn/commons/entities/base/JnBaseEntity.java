package com.jn.commons.entities.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.especifications.db.bulk.CcpBulkItem;
import com.ccp.especifications.db.bulk.CcpEntityOperationType;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;

public abstract class JnBaseEntity implements CcpEntity{

	final String name = this.getEntityName();
	final CcpEntityField[] fields;
	
	protected JnBaseEntity(CcpEntityField... fields) {
		this.fields = fields;
	}
	
	protected ArrayList<Object> getSortedPrimaryKeyValues(CcpJsonRepresentation values) {
		
		CcpJsonRepresentation primaryKeyValues = this.getPrimaryKeyValues(values);
		
		List<String> primaryKeyNames = this.getPrimaryKeyNames();
		
		Set<String> missingKeys = primaryKeyValues.getMissingKeys(primaryKeyNames);

		boolean isMissingKeys = missingKeys.isEmpty() == false;
		
		if(isMissingKeys) {
			throw new RuntimeException("It is missing the keys '" + missingKeys + "' from entity '" + this + "' in the object " + values );
		}
		
		TreeMap<String, Object> treeMap = new TreeMap<>(primaryKeyValues.content);
		Collection<Object> values2 = treeMap.values();
		ArrayList<Object> onlyPrimaryKeys = new ArrayList<>(values2);
		return onlyPrimaryKeys;
	}

	abstract public CcpBulkItem getRecordToBulkOperation(CcpJsonRepresentation values, CcpEntityOperationType operation);

	public final CcpJsonRepresentation getPrimaryKeyValues(CcpJsonRepresentation values) {
		
		List<String> onlyPrimaryKey = this.getPrimaryKeyNames();
		CcpJsonRepresentation jsonPiece = values.getJsonPiece(onlyPrimaryKey);
		return jsonPiece;
	}

	public final String getEntityName() {
		String simpleName = this.getClass().getSimpleName();
		String snackCase = new CcpStringDecorator(simpleName).text().toSnakeCase().content;
		String substring = snackCase.substring(snackCase.indexOf("entity") + 7);
		return substring;
	}

	public final CcpEntityField[] getFields() {
		return this.fields;
	}

	public String toString() {
		String entityName = this.getEntityName();
		return entityName;
	}
	
	protected List<CcpBulkItem> toCreateBulkItems(String... jsons){
		List<CcpBulkItem> collect = Arrays.asList(jsons)
		.stream().map(x -> new CcpJsonRepresentation(x))
		.map(x -> new CcpBulkItem(x, CcpEntityOperationType.create, this))
		.collect(Collectors.toList());
		return collect;
	}
	
	public boolean canSaveCopy() {
		List<String> primaryKeyNames = this.getPrimaryKeyNames();
		int size = primaryKeyNames.size();
		boolean b = size < this.fields.length;
		return b;
	}
}

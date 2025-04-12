package com.jn.commons.entities;

import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityExpurgable;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityOperationSpecification;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntitySpecifications;
import com.ccp.especifications.db.utils.decorators.configurations.CcpEntityTransferOperationEspecification;
import com.ccp.especifications.db.utils.decorators.configurations.CcpIgnoreFieldsValidation;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityConfigurator;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityExpurgableOptions;
import com.ccp.especifications.db.utils.decorators.engine.CcpEntityFactory;
import com.jn.commons.json.transformers.JnJsonTransformerPutEmailHash;
import com.jn.commons.utils.JnEntityExpurgable;

@CcpEntityExpurgable(expurgTime = CcpEntityExpurgableOptions.hourly, expurgableEntityFactory = JnEntityExpurgable.class)
@CcpEntitySpecifications(
		inactivate = @CcpEntityTransferOperationEspecification(whenRecordToTransferIsFound = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class), whenRecordToTransferIsNotFound = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class)),
		reactivate = @CcpEntityTransferOperationEspecification(whenRecordToTransferIsFound = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class), whenRecordToTransferIsNotFound = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class)),
		delete = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class),
	    save = @CcpEntityOperationSpecification(afterOperation = {}, beforeOperation = {JnJsonTransformerPutEmailHash.class}, classWithFieldsValidationsRules = CcpIgnoreFieldsValidation.class),
		cacheableEntity = true
)

public class JnEntityLoginSessionConflict implements CcpEntityConfigurator {

	public static final CcpEntity ENTITY = new CcpEntityFactory(JnEntityLoginSessionConflict.class).entityInstance;
	public static enum Fields implements CcpEntityField{
		email(true);
		
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}

	}
}

package com.jn.commons.entities.vis;

import com.ccp.especifications.db.utils.CcpEntityField;
import com.ccp.especifications.db.utils.CcpTimeOption;
import com.jn.commons.entities.JnBaseEntity;

public class VisEntityWithdrawDone extends JnBaseEntity{
	public VisEntityWithdrawDone() {
		super(false, CcpTimeOption.ddMMyyyy, Fields.values());
	}
	public static enum Fields implements CcpEntityField{
		walletReceiver(true),
		withdrawDate(true),
		walletPayer(true),
		ammount(false),
		;
		private final boolean primaryKey;

		private Fields(boolean primaryKey) {
			this.primaryKey = primaryKey;
		}

		@Override
		public boolean isPrimaryKey() {
			return this.primaryKey;
		}

	}
}

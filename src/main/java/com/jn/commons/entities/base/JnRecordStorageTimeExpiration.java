package com.jn.commons.entities.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ccp.decorators.CcpTimeDecorator;

public enum JnRecordStorageTimeExpiration{
	yyyy(Calendar.YEAR)
	,monthly(Calendar.MONTH)
	,ddMMyyyyHHmm(Calendar.MINUTE)
	,daily(Calendar.DAY_OF_MONTH)
	,ddMMyyyyHHmmss(Calendar.SECOND)
	,hourly(Calendar.HOUR_OF_DAY)
	,ddMMyyyyHHmmssSSS(Calendar.MILLISECOND), 
	;
	private final int calendarField;
	
	private JnRecordStorageTimeExpiration(int calendarField) {
		this.calendarField = calendarField;
	}

	public String getFormattedCurrentDate(Long date) {
		Date d = new Date();
		d.setTime(date);
		String format = new SimpleDateFormat(this.name()).format(d);
		return format;
	}
	
	public Long getNextTimeStamp() {
		Calendar cal = new CcpTimeDecorator().getBrazilianCalendar();
		cal.add(this.calendarField, 1);
		long timeInMillis = cal.getTimeInMillis();
		return timeInMillis;
	}
} 

package com.jn.commons.entities.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ccp.decorators.CcpTimeDecorator;

public enum JnDiposableRecordTimeExpiration{
	yearly(Calendar.YEAR, "yyyy")
	,monthly(Calendar.MONTH, "yyyyMM")
	,minute(Calendar.MINUTE, "ddMMyyyy HH:mm")
	,daily(Calendar.DAY_OF_MONTH, "ddMMyyyy")
	,second(Calendar.SECOND, "ddMMyyyy HH:mm:ss")
	,hourly(Calendar.HOUR_OF_DAY, "ddMMyyyy HH")
	,millisecond(Calendar.MILLISECOND, "ddMMyyyy HH:mm:ss.SSS"), 
	;
	private final int calendarField;
	private final String format;
	
	private JnDiposableRecordTimeExpiration(int calendarField, String format) {
		this.calendarField = calendarField;
		this.format = format;
	}

	public String getFormattedCurrentDate(Long date) {
		Date d = new Date();
		d.setTime(date);
		String format = new SimpleDateFormat(this.format).format(d);
		return format;
	}
	
	public Long getNextTimeStamp() {
		Calendar cal = new CcpTimeDecorator().getBrazilianCalendar();
		cal.add(this.calendarField, 1);
		long timeInMillis = cal.getTimeInMillis();
		return timeInMillis;
	}
} 

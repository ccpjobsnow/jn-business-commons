package com.jn.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum TimeOption{
	none{
		@Override
		public String getFormattedCurrentDate(Long time) {
			return "";
		}
	}
	,MMyyyy
	,ddMMyyyy
	,ddMMyyyyHH
	,ddMMyyyyHHmm
	,ddMMyyyyHHmmss
	,ddMMyyyyHHmmssSSS, 
	ddMM
	;

	public String getFormattedCurrentDate(Long date) {
		Date d = new Date();
		d.setTime(date);
		String format = new SimpleDateFormat(this.name()).format(d);
		return format + "_";
	}
} 

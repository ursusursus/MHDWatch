package com.ursus.mhdwatch.helpers;

import java.util.Calendar;

import com.ursus.mhdwatch.Time;

public class TimeHelper {

	
	private static TimeHelper instance;

	
	private TimeHelper() {

	}

	public static TimeHelper getInstance() {
		
		if (instance == null) {
			instance = new TimeHelper();
		}

		return instance;
	}

	public Time getCurrentTime() {

		Calendar cal = Calendar.getInstance();
		return new Time(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

	}

	public int getDayType() {
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SATURDAY)
			return 2;
		else if (day == Calendar.SUNDAY)
			return 3;
		else
			return 1;
	}

}

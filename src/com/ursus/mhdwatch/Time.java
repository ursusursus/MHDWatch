package com.ursus.mhdwatch;

public class Time {

	
	private int hours;
	private int minutes;

	
	public Time(String hours, String minutes) {
		
		this.hours = Integer.parseInt(hours);
		this.minutes = Integer.parseInt(minutes);
		
	}

	public Time(int hours, int minutes) {
		
		this.hours = hours;
		this.minutes = minutes;
		
	}

	public boolean isAfter(Time time) {

		if (this.hours > time.hours) {
			return true;
		} else if ((this.hours == time.hours) && (this.minutes > time.minutes)) {
			return true;
		} else {
			return false;
		}
	}

	public String calculateDifference(Time time) {

		int resultMinutes;
		int resultHours;
		int prenos;

		if (time.minutes < this.minutes) {
			resultMinutes = (60 - this.minutes) + time.minutes;
			prenos = 1;
		} else {
			resultMinutes = time.minutes - this.minutes;
			prenos = 0;
		}

		resultHours = time.hours - this.hours - prenos;

		String timeString = "";
		if (resultHours > 0) {
			timeString = resultHours + "h ";
		}
		timeString += resultMinutes + "m";
		
		return timeString;
	}

	public String toString() {
		
		String timeString = this.hours + ":";
		if (minutes < 10) {
			timeString += "0";
		}
		timeString += this.minutes;
		return timeString;
	}
}
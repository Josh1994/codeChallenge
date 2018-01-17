package codeChallenge;

import java.util.Calendar;
import java.util.Date;

public class PastureCover {
	Date date;
	int paddockId;
	int value;
	double paddockSize;
	
	public PastureCover(Date date, int paddockId, int value, double paddockSize) {
		this.date = date;
		this.paddockId = paddockId;
		this.value = value;
		this.paddockSize = paddockSize;
	}
	
	public boolean noMore40daysApart(Date d2) {
		
		int daysApart = daysBetween(date, d2);
		
//		System.out.println("First date "+date +" "+value+" "+ "Second date "+d2);
//		System.out.println("Days apart "+ daysApart);
		if(Math.abs(daysApart) <=40 && Math.abs(daysApart) > 0) { // Checks if the dates are within 40days
			return true;
		}
		else {
			return false;
		}
	}
	
	public int daysBetween(Date d2) {
		return (int)( (d2.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	private int daysBetween(Date d1, Date d2) {
		return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public String determineMonth(Date d2) {
		int daysApart = Math.abs(daysBetween(date, d2));
		String month = "";
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(d2);
		cal.add(Calendar.DATE, -(daysApart/2));
		switch(cal.get(Calendar.MONTH)) {
			case 0: month = "January";
					break;
			case 1: month = "Febuary";
					break;
			case 2: month = "March";
					break;
			case 3: month = "April";
					break;
			case 4: month = "May";
					break;
			case 5: month = "June";
					break;
			case 6: month = "July";
					break;
			case 7: month = "August";
					break;
			case 8: month = "September";
					break;
			case 9: month = "October";
					break;
			case 10: month = "November";
					break;
			case 11: month = "December";
					break;
		}
		return month;
	}
	

	
	
}

package com.wrkconn.common.utils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
        
public class DateUtil {

	public static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static String ISO_LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	
    private static final String DATE_FORMAT1 = "yyyy-MM-dd";
    private static final String DATE_FORMAT1_PLUS = "yyyy-MM-d";
    private static final String DATE_FORMAT2 = "yyyy";
    private static final String DATE_FORMAT3 = "MMM yyyy";
    private static final String DATE_FORMAT4 = "MM-yyyy";
    private static final String DATE_FORMAT4_PLUS = "M-yyyy";   
    private static final String DATE_FORMAT5 = "MMM dd yyyy";
    private static final String DATE_FORMAT6 = "MMM dd, yyyy";
    private static final String DATE_FORMAT6_PLUS = "MMM d, yyyy";
    private static final String DATE_FORMAT7 = "M yyyy";
    private static final String DATE_FORMAT8 = "MMM, yyyy";
	
	static Logger _log = LogManager.getLogger(DateUtil.class);
    
    public static Date getTimestampDate() {
    	TimeZone utc = TimeZone.getTimeZone("UTC");
    	Calendar cal = Calendar.getInstance(utc);
    	return cal.getTime(); 	
    }
    
    public static String getTimestamp() {
        Date d = getTimestampDate();
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utc);
        return(sdf.format(d));
        
    }
    
    public static String formatTimestamp(Date d) {    	
    	if (d != null) {
	    	SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_FORMAT);
	    	return dateFormat.format(d);
    	} else {
    		_log.warn("formatTimestamp: Date param is null. Returning empty string");
    		return "";
    	}
    }
    
    
    public static String formatTimestampUTC(Date d) {    	
    	if (d != null) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_FORMAT);
        	TimeZone utc = TimeZone.getTimeZone("UTC");
        	dateFormat.setTimeZone(utc);
        	return dateFormat.format(d);
    	} else {
    		_log.warn("formatTimestampUTC: Date param is null. Returning empty string");
    		return "";
    	}
    }   
    
    public static Date getDate(String s) {
    	Date d = null;
    	try {
    		if (s != null) {
    			SimpleDateFormat formatter = new SimpleDateFormat(ISO_FORMAT);
            	TimeZone utc = TimeZone.getTimeZone("UTC");
            	formatter.setTimeZone(utc);
    			d = formatter.parse(s);
    		}
    	} catch (ParseException e) {
    		_log.warn("getDate: ParseException while attempting to extract value for " + s);
    	}
    	return d;
    }
    
    public static Date getDateGivenFormat(String s, String f) {
    	Date d = null;
		if ((s != null) && (f != null)) {
			SimpleDateFormat formatter = new SimpleDateFormat(f);
        	TimeZone utc = TimeZone.getTimeZone("UTC");
        	formatter.setTimeZone(utc);
			try {
				d = formatter.parse(s);
			} catch (ParseException e) {
				_log.info("getDateGivenFormat: could not parse " + s + " into a Date object. Trying French locale...");
				Locale localeFr = new Locale("fr", "FR");
				formatter = new SimpleDateFormat(f, localeFr);
				formatter.setTimeZone(utc);
				try {
					d = formatter.parse(s);
				} catch (ParseException e1) {
					_log.info("getDateGivenFormat: could not parse " + s + " into a Date object. Trying Spanish locale...");
					Locale localeEs = new Locale("es", "ES");
					formatter = new SimpleDateFormat(f, localeEs);
					formatter.setTimeZone(utc);
					try {
						d = formatter.parse(s);
					} catch (ParseException e2) {
						_log.info("getDateGivenFormat: could not parse " + s + " into a Date object. Trying Portugese locale...");
						Locale localePt = new Locale("pt", "PT");
						formatter = new SimpleDateFormat(f, localePt);
						formatter.setTimeZone(utc);
						try {
							d = formatter.parse(s);
						} catch (ParseException e3) {	
							_log.warn("getDateGivenFormat: ParseException while trying to parse " + s + " into a Date object");
						}
					}
				}
            }
		} else {
			_log.warn("getDateGivenFormat: param s is null");
		}
		return d;
    }
    
    public static String getDateStringFromDate(Date d, String f) {    	
    	String dateStr = null; 	
    	if ((d != null) && (f != null)) {
	    	SimpleDateFormat dateFormat = new SimpleDateFormat(f);
        	TimeZone utc = TimeZone.getTimeZone("UTC");
        	dateFormat.setTimeZone(utc);
	    	dateStr = dateFormat.format(d.getTime());
    	}
    	return dateStr;
    }
    
    public static Date getNextUTCDayDate(Date d) {
    	
    	// create a new calendar in UTC TimeZone, set to this date and add the offset
    	Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    	gmtCal.setTime(d);
    	
    	// move forward to next day (midnight)
    	gmtCal.add(Calendar.DAY_OF_MONTH, 1);
    	
    	// now reset the GMT time to midnight
    	gmtCal.set(Calendar.HOUR_OF_DAY, 0);
    	gmtCal.set(Calendar.MINUTE, 0);
    	gmtCal.set(Calendar.SECOND, 0);
    	gmtCal.set(Calendar.MILLISECOND, 0);
    	
    	Date tomorrow  = gmtCal.getTime();
    	
    	return tomorrow;
    }
    
    public static Date getNextPacificDayDate(Date d) {
    	
    	// create a new calendar in Pacific TimeZone, set to this date and add the offset
    	Calendar pacCal = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
    	pacCal.setTime(d);
    	
    	// move forward to next day (midnight)
    	pacCal.add(Calendar.DAY_OF_MONTH, 1);
    	
    	// now reset the GMT time to midnight
    	pacCal.set(Calendar.HOUR_OF_DAY, 0);
    	pacCal.set(Calendar.MINUTE, 0);
    	pacCal.set(Calendar.SECOND, 0);
    	pacCal.set(Calendar.MILLISECOND, 0);
    	
    	Date tomorrow  = pacCal.getTime();
    	
    	return tomorrow;
    }
    
    public static Date getDateGivenDetails(int day, int month, int year) {
    	
    	// create a new calendar in Pacific TimeZone, set to this date and add the offset
    	Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    	// now set the time
    	cal.set(Calendar.YEAR, year);
    	cal.set(Calendar.MONTH, (month-1));
    	cal.set(Calendar.DAY_OF_MONTH, day);

    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	
    	Date ret  = cal.getTime();    	
    	return ret;
    }
    
    public static Date getUTCDayDate(Date d) {
    	
    	Calendar cal  = Calendar.getInstance();
    	
    	// get the current TimeZone
    	TimeZone tz = cal.getTimeZone();
    	
    	// returns the offset of this time zone from UTC at the specified date. Takes into account Daylight Savings Time etc.
    	int offsetFromUTC = tz.getOffset(d.getTime());
    	
    	// create a new calendar in UTC TimeZone, set to this date and add the offset
    	Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    	gmtCal.setTime(d);
    	gmtCal.add(Calendar.MILLISECOND, offsetFromUTC);

    	// now reset the GMT time to midnight
    	gmtCal.set(Calendar.HOUR_OF_DAY, 0);
    	gmtCal.set(Calendar.MINUTE, 0);
    	gmtCal.set(Calendar.SECOND, 0);
    	gmtCal.set(Calendar.MILLISECOND, 0);
    	
    	// return UTC (midnight) date
    	Date today  = gmtCal.getTime();
    	
    	return today;
    }
    
    public static Date getPacificDayDate(Date d) {
    	
    	// get the current TimeZone
    	TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    	
    	// create a new calendar in Pacific TimeZone, set to this date and add the offset
    	Calendar pacCal = new GregorianCalendar(tz);
    	pacCal.setTime(d);

    	// now reset the time to midnight
    	pacCal.set(Calendar.HOUR_OF_DAY, 0);
    	pacCal.set(Calendar.MINUTE, 0);
    	pacCal.set(Calendar.SECOND, 0);
    	pacCal.set(Calendar.MILLISECOND, 0);
    	
    	// return UTC (midnight) date
    	Date today  = pacCal.getTime();
    	
    	return today;
    }
    
    public static int getDiffInDays(String fromDateStr, String format) {

        int diffInDays = 0;
        if (StringUtil.isNullOrEmpty(fromDateStr)) 
        	return -1;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date fromDate = dateFormat.parse(fromDateStr);

            diffInDays = (int) ((System.currentTimeMillis() - fromDate.getTime()) / (1000 * 60 * 60 * 24));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diffInDays;
    }    

    public static int getDiffInDays(Date fromDate) {

        int diffInDays = 0;
        if (fromDate == null) 
        	return -1;

        diffInDays = (int) ((System.currentTimeMillis() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
        return diffInDays;
    }  
    
    public static Date getDateXDaysAgo(Date fromDate, int daysAgo) {
    	
    	Date xDaysAgoDate = null;    	
    	if (fromDate != null) {
    		long fromDateSecs = fromDate.getTime()/1000;
    		long numAgoSecs = daysAgo*24*60*60;
    		long xDaysAgoDateSecs = fromDateSecs - numAgoSecs;
    		xDaysAgoDate = new Date(xDaysAgoDateSecs*1000);
    	}
    	return xDaysAgoDate;
    }
    
    public static String findDateFormat(String x) {
        
        Pattern p1 = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})"); 			// p1 		= 2004-01-02
        Pattern p1plus = Pattern.compile("(\\d{4}-\\d{2}-\\d{1})"); 		// p1plus 	= 2004-01-2
        Pattern p2 = Pattern.compile("(\\d{4})");							// p2 		= 2004
        Pattern p3 = Pattern.compile("([A-Za-z ]+ \\d{4}$)");				// p3 		= January 2004
        Pattern p4 = Pattern.compile("(\\d{2}-\\d{4})");					// p4 		= 01-2004
        Pattern p4plus = Pattern.compile("(\\d{1}-\\d{4})");				// p4plus	= 1-2004
        Pattern p5 = Pattern.compile("([A-Za-z ]+ \\d{2} \\d{4}$)");		// p5		= January 02 2004
        Pattern p6 = Pattern.compile("([A-Za-z ]+ \\d{2}, \\d{4}$)");		// p6		= January 02, 2004
        Pattern p6plus = Pattern.compile("([A-Za-z ]+ \\d{1}, \\d{4}$)");	// p6plus	= January 2, 2004
        Pattern p7 = Pattern.compile("(\\d{1} \\d{4})");					// p7 		= 1 2004
        Pattern p8 = Pattern.compile("([A-Za-z]+, \\d{4})");				// p8       = Jan, 2014
        
        if (x != null) x = x.trim();
        if (StringUtil.isNullOrEmpty(x)) {
        	return "";
        }
        
        String dateFormatStr = "";
        Matcher m = p1.matcher(x);
        if(m.matches()) {
            dateFormatStr = DATE_FORMAT1;
        } else {
            m = p1plus.matcher(x);
            if(m.matches()) {
                dateFormatStr = DATE_FORMAT1_PLUS;
            } else {
            	m = p2.matcher(x);
            	if (m.matches()) {
            		dateFormatStr = DATE_FORMAT2;
            	} else {
            		m = p3.matcher(x);
            		if (m.matches()) {
            			dateFormatStr = DATE_FORMAT3;
            		} else {
            			m = p4.matcher(x);
            			if (m.matches()) {
            				dateFormatStr = DATE_FORMAT4;
            			} else {
            				m = p4plus.matcher(x);
            				if (m.matches()) {
            					dateFormatStr = DATE_FORMAT4_PLUS;
            				} else {           				
            					m = p5.matcher(x);
            					if (m.matches()) {
            						dateFormatStr = DATE_FORMAT5;
            					} else {
            						m = p6.matcher(x);
            						if (m.matches()) {
            							dateFormatStr = DATE_FORMAT6;
            						}
            						else {
            							m = p6plus.matcher(x);
            							if (m.matches()) {
            								dateFormatStr = DATE_FORMAT6_PLUS;
            							}
            							else {           							
            								m = p7.matcher(x);
            								if (m.matches()){
            									dateFormatStr = DATE_FORMAT7;
            								}
            								else {
            									m = p8.matcher(x);
            									if (m.matches()){
            										dateFormatStr = DATE_FORMAT8;
            									}
            								}
            							}
            						}
            					}
            				}
            			}
            		}
            	}
            }
        }
        return dateFormatStr;
    }

    public static Date getFirstDayMonth(int month, int year) {
        Date firstDayOfMonth = null;
        if (month >0 && year > 0) {
            String dateStr = year + "-" + String.format("%02d",month) + "-" + "01";
            String ff = DateUtil.findDateFormat(dateStr);
            firstDayOfMonth = DateUtil.getDateGivenFormat(dateStr, ff);
        }
        return firstDayOfMonth;
    }

    public static Date getLastDayMonth(int month, int year) {
        Date lastDayOfMonth = null;
        if (month >0 && year > 0) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR,year);
            c.set(Calendar.MONTH, month-1);
            int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            String dateStr = year + "-" + String.format("%02d",month) + "-" + lastDay;
            String ff = DateUtil.findDateFormat(dateStr);
            lastDayOfMonth = DateUtil.getDateGivenFormat(dateStr, ff);
        }
        return lastDayOfMonth;
    }

    // returns the next month
    // @params: month between 0-11, year between 1971 and 2019
    public static Calendar getNextMonthUntilNow(int month, int year) {	
    	
    	TimeZone utc = TimeZone.getTimeZone("UTC");
    	GregorianCalendar now = (GregorianCalendar) GregorianCalendar.getInstance(utc);
    	
    	GregorianCalendar cal = null;
    	if (month > -1 && month < 12 & year > 1970 && year < 2020) {
    		cal = (GregorianCalendar) GregorianCalendar.getInstance();
    		cal.set(Calendar.DAY_OF_MONTH, 1);
    		cal.set(Calendar.MONTH, month);
    		cal.set(Calendar.YEAR, year);
    		cal.add(Calendar.MONTH, 1);
    		// nextMonth = cal.get(Calendar.MONTH)+1;
    		// year = cal.get(Calendar.YEAR);
    		
			// check to see if the next month falls after "now". If so, return null
    		if (cal.after(now)) {
    			cal =  null;
    		}
    	}
    	
    	return cal;
    }
    
	public static String getFutureDateGivenTimeAndTimezone(int hr, int min, int sec) {
    	
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(DateUtil.getTimestampDate().getTime());
		calendar.set(Calendar.HOUR_OF_DAY, hr);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, sec);		
		Date d = calendar.getTime();
		if (d.before(DateUtil.getTimestampDate())) {
    		calendar.add(Calendar.DAY_OF_MONTH, 1);
		}		
		DateFormat formatter = new SimpleDateFormat(ISO_LOCAL_DATE_TIME_FORMAT);
		formatter.setCalendar(calendar);
		return (formatter.format(calendar.getTime()));

    }
    
    public static void main(String[] args) {  
    	
    	/*
        if ((args != null) && (args.length > 1)) {
            System.out.println("Usage: DateUtil");
        }             
        
        @SuppressWarnings("unused")
		Date d2 = getNextPacificDayDate(getTimestampDate());
        
        String ret = getFutureDateGivenTimeAndTimezone( 9, 0, 0);
        System.out.println("ret: " + ret);
        
        Date now = DateUtil.getTimestampDate();
    	Calendar nowCal = Calendar.getInstance();
    	nowCal.setTimeInMillis(now.getTime());
        for (int hr=0; hr<48; hr++) {
        	nowCal.add(Calendar.HOUR, 1);        
	        Date d = nowCal.getTime();
	        Date t = getNextUTCDayDate(d);
	        
	        System.out.println("now: " + now + "d, : " + d + ", t: " + formatTimestampUTC(t));
        } */
        

        int month = 1; // Calendar.FEBRUARY
        int year = 2009;
        Calendar cal = Calendar.getInstance();
        while (cal != null) {
        	cal = DateUtil.getNextMonthUntilNow(month, year);
        	if (cal != null) {
	        	month = cal.get(Calendar.MONTH);
	        	year = cal.get(Calendar.YEAR);
	        	System.out.println("nextMonth: " + (month+1) + ", year: " + year);
        	}
        }
       
        /*
        Date d1 = getDateGivenFormat("2014-01-02", "yyyy-MM-DD");
        System.out.println("d: " + d1);
        
        String ts = getTimestamp();
        System.out.println("Timestamp: " + ts);
        
        Date d = getTimestampDate();
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
        final TimeZone utc = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utc);
        System.out.println("Timestamp: " + sdf.format(d));
        
        String f = formatTimestamp(d);
        System.out.println("d: " + d + ", f: " + f);
        
        Date t = getNextUTCDayDate(d);
        f = formatTimestamp(d);
        System.out.println("t: " + t + ", f: " + f);
        System.out.println(sdf.format(t));
        
        Date ed = getDate(f);
        System.out.println("ed: " + ed);
        
        String ef = formatTimestamp(ed);
        System.out.println("ef: " + ef);
        
        String x = "2014-05-23";
        System.out.println(x + "--> " + findDateFormat(x));

        x = "2014";
        System.out.println(x + "--> " + findDateFormat(x));

        x = "March 2013";
        System.out.println(x + "--> " + findDateFormat(x));

        x = "Septembre de 2014";
        System.out.println(x + "--> " + findDateFormat(x));

        String p = "2004-01-02";
        String fmt = findDateFormat(p);
        Date d = getDateGivenFormat(p, fmt);   
        System.out.println(p + ": " + p + ", date: " + d);
        
        p = "2004-01-2";
        fmt = findDateFormat(p);
        d = getDateGivenFormat(p, fmt);        
        System.out.println(p + ": " + p + ", date: " + d);        
        
        p = "2004";
        fmt = findDateFormat(p);
        d = getDateGivenFormat(p, fmt);        
        System.out.println(p + ": " + p + ", date: " + d); 

        p = "January 2004";
        fmt = findDateFormat(p);
        d = getDateGivenFormat(p, fmt);        
        System.out.println(p + ": " + p + ", date: " + d); 

        p = " 01-2004";
        fmt = findDateFormat(p);
        d = getDateGivenFormat(p, fmt);        
        System.out.println(p + ": " + p + ", date: " + d); 
        
        p = "1-2004";
        fmt = findDateFormat(p);
        d = getDateGivenFormat(p, fmt);        
        System.out.println(p + ": " + p + ", date: " + d); 
        
        p = "January 02 2004";
        fmt = findDateFormat(p);
        d = getDateGivenFormat(p, fmt);        
        System.out.println(p + ": " + p + ", date: " + d); 
        
        p = "January 02, 2004";
        fmt = findDateFormat(p);
        d = getDateGivenFormat(p, fmt);        
        System.out.println(p + ": " + p + ", date: " + d); 
        
        p = "January 2, 2004";
        fmt = findDateFormat(p);
        d = getDateGivenFormat(p, fmt);        
        System.out.println(p + ": " + p + ", date: " + d); 
        
        p = "1 2004";
        fmt = findDateFormat(p);
        d = getDateGivenFormat(p, fmt);        
        System.out.println(p + ": " + p + ", date: " + d); 
    	
    	Date now = new Date();
    	Date d = getDateXDaysAgo(now, 1825);
    	System.out.println("now: " + now + ", d: " + d);
    	
    	String ds = "abril de 2012";
    	//String ds = "mai 2012";
    	String s = findDateFormat(ds);
    	System.out.println(s);
    	
        Date d = getDateGivenFormat(ds, s);
        System.out.println("d: " + d);
        */
	}
     
}

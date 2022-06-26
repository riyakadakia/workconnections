package com.wrkconn.common.utils;

import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
        
public class LoggerUtil {

	// local logger object
	static Logger _log = LogManager.getLogger(LoggerUtil.class);
	
    public static void logException(Logger sLog, Exception e) {
        sLog.error("logException: " + e);
        StackTraceElement[] s = e.getStackTrace();
        for (int i=0; i<s.length; i++)  {
            sLog.error(s[i].toString());
        }
    }

	public static void logException(Logger sLog, Throwable e) {
        sLog.error("logException: " + e);
        StackTraceElement[] s = e.getStackTrace();
        for (int i=0; i<s.length; i++)  {
            sLog.error(s[i].toString());
        }
	}
	
	public static void logTimeTaken(Logger sLog, Date start, Date end, String logMessage) {
		if (sLog != null && start != null && end != null) {
			Long timeElapsed = end.getTime() - start.getTime();
			if (timeElapsed != null & timeElapsed > 0) {
				//timeElapsed = timeElapsed/1000; // in seconds
				if (logMessage.length() > 500) {
					sLog.info("*** " + timeElapsed + "ms - " + logMessage.substring(0, 500));
				} else {
					sLog.info("*** " + timeElapsed + "ms - " + logMessage);
				}
			}
		}
	}
	
	public static void logFirstFewChars(Logger sLog, String logMessage, int numChars) {
		if (sLog != null && logMessage != null && numChars > 0 ) {
			if (logMessage.length() > numChars) {
				sLog.info("*** logFirstFewChars: " + logMessage.substring(0, numChars));
			} else {
				sLog.info("*** logFirstFewChars: " + logMessage);
			}
		}
	}
	
	public static void main(String args[]) {
		
		//Date start = DateUtil.getTimestampDate();
		//try {
		//	Thread.sleep(7234);
		//} catch (InterruptedException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		//Date end = DateUtil.getTimestampDate();
		//logTimeTaken(_log, start, end, "sleep ");
		//logTimeTaken(_log, start, end, "sleep sdfnskdjfskldjflsdf dsfsdjflksdjfls sdfjsdkljflsdjflkjsdlfjs ABCDSDFDSFSDFSDFSDFSDFSDFSD sdfhsdjflslfjs");
		logFirstFewChars(_log, "sleep ", 300);
		logFirstFewChars(_log, "sleep sdfnskdjfskldjflsdf dsfsdjflksdjfls sdfjsdkljflsdjflkjsdlfjs ABCDSDFDSFSDFSDFSDFSDFSDFSD sdfhsdjflslfjs", 20);
	}
	
}

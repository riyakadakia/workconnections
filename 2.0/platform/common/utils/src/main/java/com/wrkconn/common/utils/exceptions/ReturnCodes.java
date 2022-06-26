package com.wrkconn.common.utils.exceptions;

import java.util.HashMap;

public class
        ReturnCodes {

    // return codes
    public final static int SUCCESS = 0;
    public final static int FAIL = 1;
	public final static int DB_EXCEPTION_ERROR_CODE = 2;
	public final static int INVALID_INPUT_EXCEPTION_ERROR_CODE = 3;
	public final static int AUDIT_LOG_WRITE_EXCEPTION_ERROR_CODE = 4;
	public final static int COUNTER_INCREMENT_EXCEPTION_ERROR_CODE = 5;
	public final static int COUNTER_INITIALIZATION_EXCEPTION_ERROR_CODE = 6;
	public final static int CONFIG_NOT_FOUND_EXCEPTION_ERROR_CODE = 7;

	public static final HashMap<Integer, String> msgs = new HashMap<Integer, String>();

	static {
		msgs.put(Integer.valueOf(ReturnCodes.SUCCESS), "You're lucky today");
		msgs.put(Integer.valueOf(ReturnCodes.FAIL), "Damn, something's amiss");
		msgs.put(Integer.valueOf(ReturnCodes.DB_EXCEPTION_ERROR_CODE), "Database is behaving wonky");
		msgs.put(Integer.valueOf(ReturnCodes.INVALID_INPUT_EXCEPTION_ERROR_CODE), "One or more input parameters is invalid.");
		msgs.put(Integer.valueOf(ReturnCodes.AUDIT_LOG_WRITE_EXCEPTION_ERROR_CODE), "Whoa! Couldn't write to the audit log");
		msgs.put(Integer.valueOf(ReturnCodes.COUNTER_INCREMENT_EXCEPTION_ERROR_CODE), "Could not increment the counter. Crap!");
		msgs.put(Integer.valueOf(ReturnCodes.COUNTER_INITIALIZATION_EXCEPTION_ERROR_CODE), "Gosh, Couldn't even get the DbCounter collection to act up!");
		msgs.put(Integer.valueOf(ReturnCodes.CONFIG_NOT_FOUND_EXCEPTION_ERROR_CODE), "Config was not initialized yet.");
     }

	public static String getMessage(int code) {
		return msgs.get(code);
	}

}

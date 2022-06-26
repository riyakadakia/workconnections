package com.wrkconn.common.utils.exceptions;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Exception that can be thrown with-in server code.
// this should be the base class for any other exception that can be thrown with in server code.

public class BaseException extends Exception {

	private static final long serialVersionUID = -66132332298146805L;

	private static Logger _log = LoggerFactory.getLogger(BaseException.class);

	// default values if not set by the calling class/method
	Exception _inner = null;
	int _errorCode=ReturnCodes.FAIL;
	String _errorMessage = ReturnCodes.getMessage(ReturnCodes.FAIL);

	public static HashMap<Integer, String> NO_LOG_LIST = new HashMap<Integer, String>();

	static {
		// NO_LOG_LIST.put(new Integer(ReturnCodes.FAIL), "Default Exception");
	}
	
    public BaseException(int errorCode, String errorMessage) {
        super(errorMessage);
        this._errorCode = errorCode;
        this._errorMessage = errorMessage;
    }
	
	/**
	 * Hold on to the original Exception thrown
	 */
	public BaseException(Exception inner, int errorCode, String errorMessage) {
        super(errorMessage);
		this._inner = inner;
		this._errorMessage = errorMessage;
		this._errorCode = errorCode;
	}
	
	public Exception getInner() {
		return _inner;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return _errorMessage;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return _errorCode;
	}

	public static BaseException getBaseException(Exception e) {
		BaseException t = null;
		BaseException.log(_log, e, true);
        if (e instanceof BaseException) 
            t = (BaseException) e;
		else 
			t = new BaseException(e, ReturnCodes.FAIL, ReturnCodes.getMessage(ReturnCodes.FAIL));

		return t;
	}

	public static void log(Logger logger, Exception e, boolean skip) {
		int level = 0;
		if (e instanceof BaseException) {
			BaseException t = (BaseException) e;

			if (skip && NO_LOG_LIST.get(t.getErrorCode()) != null) 
                return;

        	logger.error("Level: " + level, e);

			while (t.getInner() != null) {
				level++;
				e = t.getInner();
                if (!(e instanceof BaseException)) {
                    logger.error("Level: " + level, e);
                    break;
				} else {
                    t = (BaseException) e;
					if (!skip || NO_LOG_LIST.get(t.getErrorCode()) == null) {
						logger.error("Level: " + level, t);
					}
				}
			}
		} else {
        	logger.error("Level: " + level, e);
		}
	}
	 
}

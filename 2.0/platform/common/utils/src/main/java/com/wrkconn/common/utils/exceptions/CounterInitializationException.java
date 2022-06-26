package com.wrkconn.common.utils.exceptions;


public class CounterInitializationException extends BaseException {

	private static final long serialVersionUID = -959284737449656008L;

	public CounterInitializationException(String errorMessage) {
		super(ReturnCodes.COUNTER_INITIALIZATION_EXCEPTION_ERROR_CODE, errorMessage);
	}

	public CounterInitializationException(Exception inner, String errorMessage) {
		super(inner, ReturnCodes.COUNTER_INITIALIZATION_EXCEPTION_ERROR_CODE, errorMessage);
	}

}

package com.wrkconn.common.utils.exceptions;


public class CounterIncrementException extends BaseException {

	private static final long serialVersionUID = -298391391658942696L;

	public CounterIncrementException(String errorMessage) {
		super(ReturnCodes.COUNTER_INCREMENT_EXCEPTION_ERROR_CODE, errorMessage);
	}

	public CounterIncrementException(Exception inner, String errorMessage) {
		super(inner, ReturnCodes.COUNTER_INCREMENT_EXCEPTION_ERROR_CODE, errorMessage);
	}

}

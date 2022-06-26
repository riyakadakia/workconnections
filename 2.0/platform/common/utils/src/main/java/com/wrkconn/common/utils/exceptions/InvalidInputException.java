package com.wrkconn.common.utils.exceptions;


public class InvalidInputException extends BaseException {

	private static final long serialVersionUID = -153268552787209976L;

	public InvalidInputException(String errorMessage) {
		super(ReturnCodes.INVALID_INPUT_EXCEPTION_ERROR_CODE, errorMessage);
	}

	public InvalidInputException(Exception inner, String errorMessage) {
		super(inner, ReturnCodes.INVALID_INPUT_EXCEPTION_ERROR_CODE, errorMessage);
	}

}

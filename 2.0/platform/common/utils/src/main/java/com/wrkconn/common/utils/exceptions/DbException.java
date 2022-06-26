package com.wrkconn.common.utils.exceptions;


public class DbException extends BaseException {

	private static final long serialVersionUID = 6494079785980256931L;

	public DbException(String errorMessage) {
		super(ReturnCodes.DB_EXCEPTION_ERROR_CODE, errorMessage);
	}

	public DbException(Exception inner, String errorMessage) {
		super(inner, ReturnCodes.DB_EXCEPTION_ERROR_CODE, errorMessage);
	}

}

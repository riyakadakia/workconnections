package com.wrkconn.common.utils.exceptions;

import com.wrkconn.common.utils.exceptions.BaseException;
import com.wrkconn.common.utils.exceptions.ReturnCodes;

public class ConfigNotFoundException extends BaseException {
	
	private static final long serialVersionUID = 165632736727293389L;

	public ConfigNotFoundException(String errorMessage) {
		super(ReturnCodes.CONFIG_NOT_FOUND_EXCEPTION_ERROR_CODE, errorMessage);
	}

	public ConfigNotFoundException(Exception inner, String errorMessage) {
		super(inner, ReturnCodes.CONFIG_NOT_FOUND_EXCEPTION_ERROR_CODE, errorMessage);
	}

}
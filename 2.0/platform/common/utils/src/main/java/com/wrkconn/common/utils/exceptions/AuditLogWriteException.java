package com.wrkconn.common.utils.exceptions;


public class AuditLogWriteException extends BaseException {

	private static final long serialVersionUID = 8490336299023545973L;

	public AuditLogWriteException(String errorMessage) {
		super(ReturnCodes.AUDIT_LOG_WRITE_EXCEPTION_ERROR_CODE, errorMessage);
	}

	public AuditLogWriteException(Exception inner, String errorMessage) {
		super(inner, ReturnCodes.AUDIT_LOG_WRITE_EXCEPTION_ERROR_CODE, errorMessage);
	}

}

package com.nexus.erp.common.exception;

import com.nexus.erp.common.result.ResultCode;

import java.util.Objects;

public class BusinessException extends RuntimeException{

    private final ResultCode resultCode;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BusinessException(ResultCode resultCode) {
        this(resultCode, resultCode.getMessage());
    }

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = Objects.requireNonNull(resultCode);
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}

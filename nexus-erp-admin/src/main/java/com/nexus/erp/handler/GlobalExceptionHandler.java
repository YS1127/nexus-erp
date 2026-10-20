package com.nexus.erp.handler;

import com.nexus.erp.common.exception.BusinessException;
import com.nexus.erp.common.result.Result;
import com.nexus.erp.common.result.ResultCode;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<Void>> handleBusinessException(BusinessException exception) {

        ResultCode resultCode = exception.getResultCode();

        log.warn("业务异常：{}", exception.getMessage());

        return ResponseEntity
                .status(resultCode.getCode())
                .body(Result.failure(resultCode, exception.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("请求参数校验失败");

        return ResponseEntity
                .badRequest()
                .body(Result.failure(
                        ResultCode.BAD_REQUEST,
                        message
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<Void>> handleConstraintException(ConstraintViolationException exception) {
        return ResponseEntity
                .badRequest()
                .body(Result.failure(
                        ResultCode.BAD_REQUEST,
                        "请求参数校验失败"
                ));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<Void>> handleMessageException(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .badRequest()
                .body(Result.failure(
                        ResultCode.BAD_REQUEST,
                        "请求体格式错误"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleException(Exception exception) {
        log.error("系统发生未处理异常", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.failure(
                        ResultCode.INTERNAL_ERROR
                ));
    }
}

package com.nexus.erp.common.result;

public enum ResultCode {
    SUCCESS(200, "操作成功"),

    BAD_REQUEST(400, "请求参数错误"),

    UNAUTHORIZED(401, "未认证"),

    FORBIDDEN(403, "无权限访问"),

    NOT_FOUND(404, "资源不存在"),

    CONFLICT(409, "业务状态冲突"),

    INTERNAL_ERROR(500, "系统内部错误");


    private final int code;

    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

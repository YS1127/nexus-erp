
package com.nexus.erp.controller;

import com.nexus.erp.common.exception.BusinessException;
import com.nexus.erp.common.result.Result;
import com.nexus.erp.common.result.ResultCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/success")
    public Result<String> success() {
        return Result.success("Nexus ERP Day 02");
    }

    @GetMapping("/business-error")
    public Result<Void> businessError() {
        throw new BusinessException(
                ResultCode.CONFLICT,
                "当前订单状态不允许取消"
        );
    }

    @GetMapping("/system-error")
    public Result<Void> systemError() {
        throw new IllegalStateException(
                "模拟系统异常"
        );
    }

    @PostMapping("/validate")
    public Result<String> validate(
            @Valid @RequestBody TestRequest request
    ) {
        return Result.success(request.name());
    }

    public record TestRequest(
            @NotBlank(message = "名称不能为空")
            String name
    ) {
    }
}

package com.nexus.erp.controller;

import com.nexus.erp.common.result.PageResult;
import com.nexus.erp.common.result.Result;
import com.nexus.erp.system.dto.CreateUserRequest;
import com.nexus.erp.system.dto.QueryUserRequest;
import com.nexus.erp.system.dto.UpdateUserRequest;
import com.nexus.erp.system.service.SysUserService;
import com.nexus.erp.system.vo.SysUserVO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/users")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }


    @GetMapping("/{id}")
    public Result<SysUserVO> getUser(@PathVariable(name = "id") Long id) {
        SysUserVO sysUserVO = this.sysUserService.getUserById(id);
        return Result.success(sysUserVO);
    }

    @PostMapping
    public Result<Long> createUser(@RequestBody @Valid CreateUserRequest request) {
        Long userId = this.sysUserService.createUser(request);
        return Result.success(userId);
    }

    @PutMapping("/{id}")
    public Result<Void> updateUser(@PathVariable(name = "id") Long userId, @RequestBody @Valid UpdateUserRequest request) {
        System.out.println(request);
        this.sysUserService.updateUser(userId, request);
        return Result.success();
    }

    @GetMapping
    public Result<PageResult<SysUserVO>> getUserPage(@Valid QueryUserRequest request) {
        PageResult<SysUserVO> result = this.sysUserService.getUserPage(request);
        return Result.success(result);
    }


    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable(name = "id") Long userId) {
        this.sysUserService.deleteUser(userId);
        return Result.success();
    }
}

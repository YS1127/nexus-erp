package com.nexus.erp.system.service;

import com.nexus.erp.common.exception.BusinessException;
import com.nexus.erp.common.result.ResultCode;
import com.nexus.erp.system.entity.SysUser;
import com.nexus.erp.system.mapper.SysUserMapper;
import com.nexus.erp.system.service.impl.SysUserServiceImpl;
import com.nexus.erp.system.vo.SysUserVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class SysUserServiceImplTest {

    private SysUserMapper sysUserMapper;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    private SysUserServiceImpl sysUserService;

    @BeforeEach
    void setUp() {
        // 1. 伪造（Mock）一个 Mapper 对象，它不会真的去查数据库
        sysUserMapper = Mockito.mock(SysUserMapper.class);
        // 2. 将伪造的 Mapper 注入给真实的 Service 实现类// 2. 将伪造的 Mapper 注入给真实的 Service 实现类
        sysUserService = new SysUserServiceImpl(sysUserMapper, passwordEncoder);
    }

    @Test
    void shouldReturnUserWhenUserExists() {
        // 1. 准备假数据（Stub 打桩）
        SysUser user = new SysUser();
        user.setId(1L);
        user.setUsername("admin");
        user.setNickname("系统管理员");
        // 告诉 Mock 对象：如果有人调用 selectById(1L)，就返回这个 user
        when(sysUserMapper.selectById(1L)).thenReturn(user);

        // 2. 执行真实的 Service 方法
        SysUserVO result = sysUserService.getUserById(1L);

        // 3. 断言（Assert）：验证结果是否符合预期
        assertNotNull(result);
        assertEquals("admin", result.getUsername());

        // 4. 验证（Verify）：确认 Mapper 的查询方法真的被调用了 1 次
        Mockito.verify(sysUserMapper).selectById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // 1. 打桩：模拟数据库里查不到，返回 null
        when(sysUserMapper.selectById(999L)).thenReturn(null);

        // 2. 断言异常：执行方法并捕获异常
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> sysUserService.getUserById(999L)
        );

        // 3. 验证异常里的错误码是否正确
        assertEquals(ResultCode.NOT_FOUND, exception.getResultCode());
    }

    @Test
    void shouldRejectInvalidUserId() {
        // 1. 断言异常
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> sysUserService.getUserById(0L) // 传入非法 ID：0
        );

        // 2. 验证错误码为参数错误
        assertEquals(ResultCode.BAD_REQUEST, exception.getResultCode());

        // 3. 核心验证：确认完全没有去碰数据库
        verifyNoInteractions(sysUserMapper);
    }
}

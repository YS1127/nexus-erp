package com.nexus.erp.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nexus.erp.common.exception.BusinessException;
import com.nexus.erp.common.result.PageResult;
import com.nexus.erp.common.result.ResultCode;
import com.nexus.erp.system.dto.CreateUserRequest;
import com.nexus.erp.system.dto.QueryUserRequest;
import com.nexus.erp.system.dto.UpdateUserRequest;
import com.nexus.erp.system.entity.SysUser;
import com.nexus.erp.system.mapper.SysUserMapper;
import com.nexus.erp.system.service.SysUserService;
import com.nexus.erp.system.vo.SysUserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final PasswordEncoder passwordEncoder;

    public SysUserServiceImpl(SysUserMapper sysUserMapper, PasswordEncoder passwordEncoder) {
        this.sysUserMapper = sysUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Long createUser(CreateUserRequest request) {
        System.out.println(request);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, request.username());
        SysUser sysUser = this.sysUserMapper.selectOne(queryWrapper);
        if (sysUser != null) {
            throw new BusinessException(ResultCode.CONFLICT, "用户名已经存在");
        }
        SysUser insertUser = new SysUser();
        BeanUtils.copyProperties(request, insertUser);
        insertUser.setPassword(passwordEncoder.encode(request.password()));
        try {
            this.sysUserMapper.insert(insertUser);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ResultCode.CONFLICT, "用户名已经存在");
        }
        return insertUser.getId();
    }

    @Override
    public void updateUser(Long userId, UpdateUserRequest request) {
        if (userId == null || userId <= 0) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "用户id非法");
        }
        SysUser sysUser = this.sysUserMapper.selectById(userId);
        if (sysUser == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        sysUser.setNickname(request.nickname());
        sysUser.setEmail(request.email());
        sysUser.setStatus(request.status());
        this.sysUserMapper.updateById(sysUser);
    }

    @Override
    public SysUserVO getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(
                    ResultCode.BAD_REQUEST,
                    "用户ID必须大于0"
            );
        }
        SysUser sysUser = this.sysUserMapper.selectById(id);
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(sysUser, sysUserVO);
        return sysUserVO;
    }

    @Override
    public void deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(
                    ResultCode.BAD_REQUEST,
                    "用户ID必须大于0"
            );
        }
        this.sysUserMapper.deleteById(id);
    }

    @Override
    public PageResult<SysUserVO> getUserPage(QueryUserRequest request) {
        Page<SysUser> page = new Page<>(request.getPageNum(), request.getPageSize());

        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery(SysUser.class);
        wrapper.like(
                request.getUsername() != null && !request.getUsername().isBlank(),
                SysUser::getUsername,
                request.getUsername()
        );

        wrapper.orderByDesc(SysUser::getCreateTime);

        Page<SysUser> result = this.sysUserMapper.selectPage(page, wrapper);
        PageResult<SysUserVO> pageResult = new PageResult<>();
        pageResult.setPageNum(result.getCurrent());
        pageResult.setPageSize(result.getSize());
        pageResult.setTotal(result.getTotal());
        List<SysUserVO> vos = new ArrayList<>();
        result.getRecords().forEach(sysUser -> {
            SysUserVO sysUserVO = new SysUserVO();
            BeanUtils.copyProperties(sysUser, sysUserVO);
            vos.add(sysUserVO);
        });
        pageResult.setRecords(vos);
        return pageResult;
    }
}

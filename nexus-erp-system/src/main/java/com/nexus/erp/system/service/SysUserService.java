
package com.nexus.erp.system.service;

import com.nexus.erp.common.result.PageResult;
import com.nexus.erp.system.dto.CreateUserRequest;
import com.nexus.erp.system.dto.QueryUserRequest;
import com.nexus.erp.system.dto.UpdateUserRequest;
import com.nexus.erp.system.entity.SysUser;
import com.nexus.erp.system.vo.SysUserVO;

public interface SysUserService {

    Long createUser(CreateUserRequest request);

    void updateUser(Long id, UpdateUserRequest request);

    SysUserVO getUserById(Long id);

    void deleteUser(Long id);

    PageResult<SysUserVO> getUserPage(QueryUserRequest request);
}

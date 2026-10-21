
package com.nexus.erp.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank(message = "用户名不能为空")
        @Size(max = 50, message = "用户名不能超过50个字符")
        String username,

        @NotBlank(message = "密码不能为空")
        @Size(min = 8, max = 72,
                message = "密码长度必须为8到72个字符")
        String password,

        @NotBlank(message = "昵称不能为空")
        @Size(max = 50, message = "昵称不能超过50个字符")
        String nickname,

        @Email(message = "邮箱格式不正确")
        String email
) {
}

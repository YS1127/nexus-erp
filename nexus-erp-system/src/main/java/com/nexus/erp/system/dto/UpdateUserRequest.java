
package com.nexus.erp.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @NotBlank(message = "昵称不能为空")
        @Size(max = 50)
        String nickname,

        @Email
        String email,

        Integer status
) {
}

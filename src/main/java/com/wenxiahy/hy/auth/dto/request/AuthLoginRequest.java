package com.wenxiahy.hy.auth.dto.request;

import com.wenxiahy.hy.common.support.BaseRequest;

import javax.validation.constraints.NotBlank;

/**
 * @Author zhouw
 * @Description
 * @Date 2020-12-17
 */
public class AuthLoginRequest extends BaseRequest {

    @NotBlank(message = "username不能为空")
    private String username;

    @NotBlank(message = "password不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

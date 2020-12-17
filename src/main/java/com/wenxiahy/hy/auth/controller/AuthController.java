package com.wenxiahy.hy.auth.controller;

import com.wenxiahy.hy.auth.dto.request.AuthLoginRequest;
import com.wenxiahy.hy.auth.service.IAuthService;
import com.wenxiahy.hy.common.bean.auth.AuthenticationUser;
import com.wenxiahy.hy.common.bean.entity.User;
import com.wenxiahy.hy.common.support.BaseController;
import com.wenxiahy.hy.common.support.HyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author zhouw
 * @Description
 * @Date 2020-12-16
 */
@RestController
@RequestMapping("/auth/v1")
public class AuthController extends BaseController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public HyResponse<String> login(@Valid @RequestBody AuthLoginRequest request, BindingResult bindingResult) {
        User user = authService.login(request.getUsername(), request.getPassword());
        if (user == null) {
            return error("用户不存在");
        }

        String token = authService.auth(user);
        if (token == null) {
            return error("登录失败");
        }

        // 保存到redis

        return ok(token);
    }

    @PostMapping("/valid")
    public HyResponse<AuthenticationUser> valid(String token) {
        if (token == null) {
            return error();
        }

        AuthenticationUser authUser = authService.valid(token);
        if (authUser == null) {
            return error();
        }

        // 查询redis
        String key = authUser.getUsername();
        String tokenRedis = key;
        if (!token.equals(tokenRedis)) {
            // redis中的token已经过期，或者该用户在另外一个地方登录，导致本次请求中的的token失效，需要重新登录
            return error();
        }

        // 刷新REDIS超期时间

        return ok(authUser);
    }
}

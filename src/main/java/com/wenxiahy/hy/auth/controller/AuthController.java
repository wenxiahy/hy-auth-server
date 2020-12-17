package com.wenxiahy.hy.auth.controller;

import com.wenxiahy.hy.auth.dto.request.AuthLoginRequest;
import com.wenxiahy.hy.auth.service.IAuthService;
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
        // 保存到redis

        return ok(token);
    }
}

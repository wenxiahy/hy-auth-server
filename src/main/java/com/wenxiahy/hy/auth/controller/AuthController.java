package com.wenxiahy.hy.auth.controller;

import com.wenxiahy.hy.common.support.BaseController;
import com.wenxiahy.hy.common.support.HyResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhouw
 * @Description
 * @Date 2020-12-16
 */
@RestController
@RequestMapping("/auth/v1")
public class AuthController extends BaseController {

    @PostMapping("/login")
    public HyResponse<String> login(String username, String password) {
        return ok("success");
    }
}

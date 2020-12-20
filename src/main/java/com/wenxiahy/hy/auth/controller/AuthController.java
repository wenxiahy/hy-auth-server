package com.wenxiahy.hy.auth.controller;

import com.wenxiahy.hy.auth.dto.request.AuthLoginRequest;
import com.wenxiahy.hy.auth.service.IAuthService;
import com.wenxiahy.hy.common.bean.auth.AuthenticationUser;
import com.wenxiahy.hy.common.bean.entity.User;
import com.wenxiahy.hy.common.support.BaseController;
import com.wenxiahy.hy.common.support.HyResponse;
import com.wenxiahy.hy.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.wenxiahy.hy.auth.constant.AuthConstants.TOKEN_REDIS_EXPIRES;
import static com.wenxiahy.hy.auth.constant.AuthConstants.TOKEN_REDIS_KEY_PREFIX;

/**
 * @Author zhouw
 * @Description
 * @Date 2020-12-16
 */
@RestController
@RequestMapping("/auth/v1")
public class AuthController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IAuthService authService;

    @Resource(name = "commonRedisService")
    private RedisService commonRedisService;

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
        String key = TOKEN_REDIS_KEY_PREFIX + user.getUsername();
        boolean redisRst = commonRedisService.set(key, token, TOKEN_REDIS_EXPIRES);
        if (redisRst) {
            return ok(token);
        } else {
            LOGGER.error("redis缓存token失败，key：{}，value：{}", key, token);
            return error();
        }
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
        String key = TOKEN_REDIS_KEY_PREFIX + authUser.getUsername();
        String tokenRedis = (String) commonRedisService.get(key);
        if (!token.equals(tokenRedis)) {
            // redis中的token已经过期，或者该用户在另外一个地方登录，导致本次请求中的的token失效，需要重新登录
            return error();
        }

        // 刷新redis超期时间
        commonRedisService.expire(key, TOKEN_REDIS_EXPIRES);

        return ok(authUser);
    }
}

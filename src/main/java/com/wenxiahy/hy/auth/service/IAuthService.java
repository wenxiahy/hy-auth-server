package com.wenxiahy.hy.auth.service;

import com.wenxiahy.hy.common.bean.auth.AuthenticationUser;
import com.wenxiahy.hy.data.user.User;

/**
 * @Author zhouw
 * @Description
 * @Date 2020-12-17
 */
public interface IAuthService {

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

    /**
     * 授权，获取token
     *
     * @param user
     * @return
     */
    String auth(User user);

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    AuthenticationUser valid(String token);
}

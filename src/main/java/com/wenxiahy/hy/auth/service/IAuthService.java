package com.wenxiahy.hy.auth.service;

import com.wenxiahy.hy.common.bean.entity.User;

/**
 * @Author zhouw
 * @Description
 * @Date 2020-12-17
 */
public interface IAuthService {

    String auth(User user);

    User login(String username, String password);
}

package com.wenxiahy.hy.auth.service.impl;

import com.wenxiahy.hy.auth.service.IAuthService;
import com.wenxiahy.hy.auth.util.AesEncryptUtils;
import com.wenxiahy.hy.common.bean.auth.AuthenticationUser;
import com.wenxiahy.hy.common.bean.entity.User;
import com.wenxiahy.hy.common.util.Base64Utils;
import com.wenxiahy.hy.common.util.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author zhouw
 * @Description
 * @Date 2020-12-17
 */
@Service
public class AuthServiceImpl implements IAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public User login(String username, String password) {
        if ("xiaoming".equals(username) && "123456".equals(password)) {
            User user = new User();
            user.setUserId(10000);
            user.setUsername(username);
            user.setPassword(password);
            return user;
        }

        return null;
    }

    @Override
    public String auth(User user) {
        try {
            AuthenticationUser authUser = new AuthenticationUser();
            authUser.setUserId(user.getUserId());
            authUser.setUsername(user.getUsername());
            authUser.setDeviceId("iPhone 11 Pro");
            authUser.setTimestamp(System.currentTimeMillis());

            // 1. 将authUser转成json字符串，进行Base64编码
            String json = JacksonUtils.object2Json(authUser);
            String payload = Base64Utils.encryptUrlSafeBase64(json);

            // 2. 使用AES加密
            return AesEncryptUtils.encrypt(payload);
        } catch (Exception e) {
            LOGGER.error("生成token异常", e);
            return null;
        }
    }

    @Override
    public AuthenticationUser valid(String token) {
        try {
            String decrypt = AesEncryptUtils.decrypt(token);
            String payload = Base64Utils.decryptUrlSafeBase64(decrypt);
            AuthenticationUser authUser = JacksonUtils.json2Object(payload, AuthenticationUser.class);
            return authUser;
        } catch (Exception e) {
            return null;
        }
    }
}

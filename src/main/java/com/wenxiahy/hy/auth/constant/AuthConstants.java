package com.wenxiahy.hy.auth.constant;

/**
 * @Author zhouw
 * @Description
 * @Date 2020-12-20
 */
public interface AuthConstants {

    String TOKEN_REDIS_KEY_PREFIX = "user:token:";

    long TOKEN_REDIS_EXPIRES = 7 * 24 * 60 * 60;
}

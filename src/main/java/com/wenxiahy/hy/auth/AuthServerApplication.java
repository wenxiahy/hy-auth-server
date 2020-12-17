package com.wenxiahy.hy.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author zhouw
 * @Description
 * @Date 2020-12-16
 */
@SpringBootApplication(scanBasePackages = {"com.wenxiahy.hy"})
@EnableDiscoveryClient
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}

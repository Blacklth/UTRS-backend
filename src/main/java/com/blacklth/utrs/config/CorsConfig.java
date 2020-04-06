package com.blacklth.utrs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域支持配置
 * @author : LiaoTianHong
 * @date : 2019/7/29 19:08
 */
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")//允许域名访问，如果*，代表所有域名
                        .allowedMethods("PUT", "DELETE","GET","POST")
                        .allowedHeaders("*")
                        .exposedHeaders("access-control-allow-headers",
                                "access-control-allow-methods",
                                "access-control-allow-origin",
                                "access-control-allow-credentials",
                                "access-control-max-age",
                                "X-Frame-Options",
                                "Token")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}

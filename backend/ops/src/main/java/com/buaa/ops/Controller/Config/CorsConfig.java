package com.buaa.ops.Controller.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")    // 配置跨域请求支持的方式
                .allowCredentials(true)    // 配置是否允许发送Cookie，用于 凭证请求， 默认不发送cookie
                .maxAge(3600)
                .allowedHeaders("*");
    }
}

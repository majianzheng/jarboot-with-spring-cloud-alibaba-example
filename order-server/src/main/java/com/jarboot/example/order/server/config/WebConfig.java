package com.jarboot.example.order.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final String[] methods = new String[] {"GET", "POST", "PUT", "DELETE"};
        // 设定全局跨域
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods(methods)
                .allowedHeaders("*").exposedHeaders("*");
    }
}

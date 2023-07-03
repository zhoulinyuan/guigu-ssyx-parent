package com.atguigu.ssyx.common.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Configuration
public class LoginMvcConfigurerAdapter extends WebMvcConfigurationSupport {
    // @Override
    @Resource
    private RedisTemplate redisTemplate;
     public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(new UserLoginInterceptor(redisTemplate))
                 .addPathPatterns("/api/**")//需要拦截的路径
                         .excludePathPatterns("/api/user/weixin/wxLogin/*");
         super.addInterceptors(registry);
     }
}

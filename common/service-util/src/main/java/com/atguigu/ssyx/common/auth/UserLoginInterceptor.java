package com.atguigu.ssyx.common.auth;

import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.utils.JwtHelper;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor implements HandlerInterceptor {
    private RedisTemplate redisTemplate;
    public UserLoginInterceptor(RedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        this.getUserLoginVo(request);
        return true;
    }

    private void getUserLoginVo(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)){
            Long userId = JwtHelper.getUserId(token);
            UserLoginVo userLoginVo =(UserLoginVo) redisTemplate.opsForValue().get(RedisConst.USER_LOGIN_KEY_PREFIX + userId);
            if (userLoginVo!=null){
                AuthContextHolder.setUserId(userLoginVo.getUserId());
                AuthContextHolder.setWareId(userLoginVo.getWareId());

                AuthContextHolder.setUserLoginVo(userLoginVo);
            }

        }
    }
}

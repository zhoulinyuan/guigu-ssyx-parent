package com.atguigu.ssyx.common.auth;

import com.atguigu.ssyx.vo.user.UserLoginVo;

public class AuthContextHolder {

    //用户id
    private static ThreadLocal<Long> userId=new ThreadLocal<>();
    //用户仓库id
    private static ThreadLocal<Long> wareId=new ThreadLocal<>();
    //用户信息对象
    private static ThreadLocal<UserLoginVo> userLoginVo=new ThreadLocal<>();

    public static void setUserId(Long id){
        userId.set(id);
    }
    public static Long getUserId(){
        return userId.get();
    }
    public static void setWareId(Long id){
        wareId.set(id);
    }
    public static Long getWareId(){
        return wareId.get();
    }
    public static void setUserLoginVo(UserLoginVo vo){
        userLoginVo.set(vo);
    }
    public static UserLoginVo getUserLoginVo(){
        return userLoginVo.get();
    }
}

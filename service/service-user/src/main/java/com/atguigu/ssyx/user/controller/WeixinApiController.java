package com.atguigu.ssyx.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.ssyx.common.auth.AuthContextHolder;
import com.atguigu.ssyx.common.constant.RedisConst;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.utils.JwtHelper;
import com.atguigu.ssyx.enums.UserType;
import com.atguigu.ssyx.model.user.User;
import com.atguigu.ssyx.user.service.UserService;
import com.atguigu.ssyx.user.utils.ConstantPropertiesUtil;
import com.atguigu.ssyx.user.utils.HttpClientUtils;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import com.atguigu.ssyx.vo.user.UserLoginVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user/weixin")
public class WeixinApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    // 用户微信授权登录
    @GetMapping("/wxLogin/{code}")
    public Result loginEx(@PathVariable String code) throws Exception {
        // 1. 获取临时票据code
        // 2. 拿着code+小程序id+秘钥，请求微信接口服务
        String wxOpenAppId = ConstantPropertiesUtil.WX_OPEN_APP_ID;
        String wxOpenAppSecret = ConstantPropertiesUtil.WX_OPEN_APP_SECRET;
        StringBuffer url = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&js_code=%s")
                .append("&grant_type=authorization_code");
        String tokenUrl = String.format(url.toString(),
                wxOpenAppId,
                wxOpenAppSecret,
                code);
        String result = HttpClientUtils.get(tokenUrl);


        // 3. 请求微信接口服务，返回两个值 session_key 和 openid
        JSONObject jsonObject = JSONObject.parseObject(result);
        String session_key = jsonObject.getString("session_key");
        String  openid = jsonObject.getString("openid");
        // 4. 添加微信用户信息到数据库里面
           //// 4.1 先判断数据库里面是否存在相同的openid，如果存在，直接返回登录成功
        User user=userService.getUserByOpenId(openid);
        if(user==null){
            user=new User();
            user.setOpenId(openid);
            user.setNickName(openid);
            user.setPhotoUrl("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userService.save(user);
        }
           //// 4.2 如果不存在，添加到数据库里面
        // 5. 根据usrid查询提货点和团长信息
            // 提货点
            // 团长
        LeaderAddressVo leaderAddressVo=userService.getLeaderAddressByUserId(user.getId());
        // 6. 生成jwt token，
        String token = JwtHelper.createToken(user.getId(), user.getNickName());
        // 7. 获取当前登录用户信息，放到redis里面，设置有效时间
        UserLoginVo userLoginVo = userService.getUserLoginVo(user.getId());
        redisTemplate.opsForValue().set(RedisConst.USER_LOGIN_KEY_PREFIX+user.getId(),
                userLoginVo,
                RedisConst.USERKEY_TIMEOUT,
                TimeUnit.DAYS);

        // 8. 返回登录成功信息
        HashMap<String, Object> map = new HashMap<>();
        map.put("user",user);
        map.put("leaderAddressVo",leaderAddressVo);
        map.put("token",token);
        return Result.ok(map);
    }
    @PostMapping("/auth/updateUser")
    @ApiOperation(value = "更新用户昵称与头像")
    public Result updateUser(@RequestBody User user) {
        User user1 = userService.getById(AuthContextHolder.getUserId());
        //把昵称更新为微信用户
        user1.setNickName(user.getNickName().replaceAll("[ue000-uefff]", "*"));
        user1.setPhotoUrl(user.getPhotoUrl());
        userService.updateById(user1);
        return Result.ok(null);
    }
}

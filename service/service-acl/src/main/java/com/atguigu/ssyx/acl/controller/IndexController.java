package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/admin/acl/index")
@CrossOrigin
@Api(value = "后台登录")
public class IndexController {
    @PostMapping("login")
    @ApiOperation(value = "登录")
    public Result login(){
        HashMap<String, String> map = new HashMap<>();
        map.put("token","token-admin");
        return Result.ok(map);
    }
    @GetMapping("info")
    @ApiOperation(value = "获取用户信息")
    public Result info(){
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name","admin");
        objectObjectHashMap.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(objectObjectHashMap);
    }
    @PostMapping("logout")
    @ApiOperation(value = "退出登录")
    public Result logout(){
        return Result.ok(null);
    }
}

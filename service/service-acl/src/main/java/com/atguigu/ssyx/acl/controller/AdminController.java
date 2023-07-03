package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.utils.MD5;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.atguigu.ssyx.vo.user.UserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/user")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    //用户列表
    @GetMapping("{current}/{limit}")
    public Result index(@PathVariable Long current,
                        @PathVariable Long limit,
                        AdminQueryVo adminQueryVo){
        Page<Admin> userPage = new Page<>(current, limit);
        IPage<Admin> pageModel= adminService.selectPage(userPage,adminQueryVo);
        return Result.ok(pageModel);
    }

    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        Admin admin = adminService.getById(id);
        return Result.ok(admin);
    }
    //添加用户
    @PostMapping("save")
    public Result save(@RequestBody Admin admin){
        String password = admin.getPassword();
        String password2 = MD5.encrypt(password);
        admin.setPassword(password2);
        boolean save = adminService.save(admin);
        if(save){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    //删除用户
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        boolean remove = adminService.removeById(id);
        if(remove){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    //修改用户
    @PutMapping("update")
    public Result updateById(@RequestBody Admin admin){
        boolean update = adminService.updateById(admin);
        if(update){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    //批量删除
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody Long[] ids){
        boolean batchRemove = adminService.removeByIds(Arrays.asList(ids));
        if(batchRemove){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    //获取所有角色，和根据用户id获取用户角色
    @GetMapping("toAssign/{id}")
    public Result toAssign(@PathVariable Long id){
        Map<String,Object> map= roleService.getRoleByAadminId(id);
        return Result.ok(map);
    }
    @PostMapping("doAssign")
    public Result doAssign(@RequestParam Long adminId,@RequestParam Long[] roleId){
        roleService.saveAdminRole(adminId,roleId);
        return Result.ok(null);
    }
}

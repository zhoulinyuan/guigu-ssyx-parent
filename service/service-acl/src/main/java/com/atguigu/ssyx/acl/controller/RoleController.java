package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "角色管理")
@RestController
@RequestMapping("/admin/acl/role")
@CrossOrigin
public class RoleController {
    @Autowired
    private RoleService roleService;
    //分页
    @GetMapping("{current}/{limit}")
    public Result pageList(@PathVariable Long current,
                           @PathVariable Long limit,
                           RoleQueryVo roleQueryVo){
        Page<Role> rolePage = new Page<>(current, limit);
        IPage<Role> pageModel= roleService.selectRolePage(rolePage,roleQueryVo);
        return Result.ok(pageModel);
    }
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        Role role = roleService.getById(id);
        return Result.ok(role);
    }
    @PostMapping("save")
    public Result save(@RequestBody Role role){
        boolean save = roleService.save(role);
        if(save){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    @PutMapping("update")
    public Result updateById(@RequestBody Role role){
        boolean update = roleService.updateById(role);
        if(update){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        boolean remove = roleService.removeById(id);
        if(remove){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    //批量删除
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        boolean remove = roleService.removeByIds(idList);
        if(remove){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
}

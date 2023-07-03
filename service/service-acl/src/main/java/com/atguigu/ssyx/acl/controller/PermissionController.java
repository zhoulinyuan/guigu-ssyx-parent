package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.acl.service.RolePermissionService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Permission;
import com.atguigu.ssyx.model.acl.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/permission")
@CrossOrigin
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;
    //查询所有菜单
    @GetMapping
    public Result indexAllPermission(){
        List<Permission> list = permissionService.queryAllPermission();
        return Result.ok(list);
    }
    //添加菜单
    @PostMapping("save")
    public Result save(@RequestBody Permission permission){
        boolean save = permissionService.save(permission);
        if(save){
            return Result.ok(null);
    }else {
            return Result.fail(null);
        }
    }

    //修改
    @PutMapping("update")
    public Result update(@RequestBody Permission permission){
        boolean update = permissionService.updateById(permission);
        if(update){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    //删除
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        permissionService.removeChildById(id);

        return Result.ok(null);
    }

//    getAssign(roleId) {
//        return request({
//                url: `${api_name}/toAssign/${roleId}`,
//        method: 'get'
//    })
//    },

//    doAssign(roleId, permissionId) {
//        return request({
//                url: `${api_name}/doAssign`,
//        method: "post",
//                params: {roleId, permissionId}
//    })
//    }

}

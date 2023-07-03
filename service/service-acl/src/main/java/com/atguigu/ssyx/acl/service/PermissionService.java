package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author John
* @description 针对表【permission(权限)】的数据库操作Service
* @createDate 2023-06-24 14:36:38
*/
public interface PermissionService extends IService<Permission> {

    List<Permission> queryAllPermission();

    void removeChildById(Long id);


}

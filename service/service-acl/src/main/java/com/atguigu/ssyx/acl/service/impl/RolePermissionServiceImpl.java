package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.model.acl.Permission;
import com.atguigu.ssyx.model.acl.Role;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.model.acl.RolePermission;
import com.atguigu.ssyx.acl.service.RolePermissionService;
import com.atguigu.ssyx.acl.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author John
* @description 针对表【role_permission(角色权限)】的数据库操作Service实现
* @createDate 2023-06-24 16:41:48
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{


}





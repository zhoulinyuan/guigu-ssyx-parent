package com.atguigu.ssyx.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.acl.service.AdminRoleService;
import com.atguigu.ssyx.acl.mapper.AdminRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author John
* @description 针对表【admin_role(用户角色)】的数据库操作Service实现
* @createDate 2023-06-24 11:26:21
*/
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole>
    implements AdminRoleService{

}





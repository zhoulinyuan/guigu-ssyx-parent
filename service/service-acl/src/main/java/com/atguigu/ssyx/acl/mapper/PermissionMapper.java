package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author John
* @description 针对表【permission(权限)】的数据库操作Mapper
* @createDate 2023-06-24 14:36:38
* @Entity com.atguigu.ssyx.acl.Permission
*/
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

}





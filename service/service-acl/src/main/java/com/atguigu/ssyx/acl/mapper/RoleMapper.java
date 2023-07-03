package com.atguigu.ssyx.acl.mapper;

import com.atguigu.ssyx.model.acl.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author John
* @description 针对表【role(角色)】的数据库操作Mapper
* @createDate 2023-06-23 17:16:50
* @Entity com.atguigu.ssyx.acl.Role
*/
@Repository
public interface RoleMapper extends BaseMapper<Role> {

}





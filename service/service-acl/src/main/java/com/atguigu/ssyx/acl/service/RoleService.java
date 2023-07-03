package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author John
* @description 针对表【role(角色)】的数据库操作Service
* @createDate 2023-06-23 17:16:50
*/

public interface RoleService extends IService<Role> {

    IPage<Role> selectRolePage(Page<Role> rolePage, RoleQueryVo roleQueryVo);

    Map<String, Object> getRoleByAadminId(Long id);

    void saveAdminRole(Long adminId, Long[] roleId);
}

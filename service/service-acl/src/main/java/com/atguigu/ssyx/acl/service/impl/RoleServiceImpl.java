package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.service.AdminRoleService;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.acl.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author John
* @description 针对表【role(角色)】的数据库操作Service实现
* @createDate 2023-06-23 17:16:50
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{
    @Autowired
    private AdminRoleService adminRoleService;
    @Override//条件分页查询
    public IPage<Role> selectRolePage(Page<Role> rolePage, RoleQueryVo roleQueryVo) {
        String roleName = roleQueryVo.getRoleName();
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (roleName != null && !"".equals(roleName)) {
            wrapper.like(Role::getRoleName, roleName).orderByDesc(Role::getCreateTime);
        }
        wrapper.orderByDesc(Role::getCreateTime);
        IPage<Role> rolePage1 = baseMapper.selectPage(rolePage, wrapper);
        return rolePage1;
    }

    @Override
    public Map<String, Object> getRoleByAadminId(Long id) {


        // 获取所有角色
        List<Role> allRolesList = baseMapper.selectList(null);

        // 根据用户ID查询用户分配的角色列表
        //根据用户id查询 用户角色关系表 admin_role 查询用户分配角色id列表
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId,id);
        List<AdminRole> adminRoleList = adminRoleService.list(wrapper);
        List<Long> roleIdList = adminRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        //创建新的list集合，用于存储用户配置角色
        ArrayList<Role> assignRoleList = new ArrayList<>();
        //遍历所有角色列表，得到每个角色
        //判断当前角色是否被分配
        for (Role role : allRolesList) {
            //如果角色已经被分配，将角色对象放入assignRoleList
            if (roleIdList.contains(role.getId())) {
                assignRoleList.add(role);
            }
        }
        HashMap<String, Object> result = new HashMap<>();
        //所有角色
        result.put("allRolesList",allRolesList);
        //用户已经分配的角色
        result.put("assignRoles",assignRoleList);


        return result;

    }

    @Override
    public void saveAdminRole(Long adminId, Long[] roleId) {
        //先删除用户角色关系表中的数据
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId,adminId);
        adminRoleService.remove(wrapper);
        //再添加新的用户角色关系数据
        ArrayList<AdminRole> adminRoleList = new ArrayList<>();
        for (Long rid : roleId) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(rid);
            adminRoleList.add(adminRole);
        }
        adminRoleService.saveBatch(adminRoleList);
    }
}





package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.utils.PermissionHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.model.acl.Permission;
import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author John
* @description 针对表【permission(权限)】的数据库操作Service实现
* @createDate 2023-06-24 14:36:38
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

    @Override
    public List<Permission> queryAllPermission() {
        //1.查询所有菜单
        List<Permission> allPermissionList = baseMapper.selectList(null);

        //转换要求数据格式
        List<Permission> result= PermissionHelper.buildPermission(allPermissionList);
        return result;
    }

    @Override
    public void removeChildById(Long id) {
        ArrayList<Long> idList = new ArrayList<>();
        this.getAllPermissionChildrenId(id,idList);
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }
    private void getAllPermissionChildrenId(Long id, ArrayList<Long> idList) {
        //根据当前菜单id查询所有子菜单id
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPid,id);
        List<Permission> childList = baseMapper.selectList(wrapper);
        //递归查询是否还有子菜单，有就继续递归
        childList.stream().forEach(item->{
            idList.add(item.getId());
            this.getAllPermissionChildrenId(item.getId(),idList);
        });
    }
}





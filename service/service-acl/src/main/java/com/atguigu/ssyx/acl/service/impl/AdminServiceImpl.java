package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.acl.mapper.AdminMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author John
* @description 针对表【admin(用户表)】的数据库操作Service实现
* @createDate 2023-06-23 19:02:38
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

    @Override
    public IPage<Admin> selectPage(Page<Admin> userPage, AdminQueryVo adminQueryVo) {
        String username = adminQueryVo.getUsername();
        String name = adminQueryVo.getName();
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(username) ){
            wrapper.like(Admin::getUsername,username);
        }
        IPage<Admin> adminPage = baseMapper.selectPage(userPage, wrapper);
        return adminPage;
    }
}





package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author John
* @description 针对表【admin(用户表)】的数据库操作Service
* @createDate 2023-06-23 19:02:38
*/
public interface AdminService extends IService<Admin> {

    IPage<Admin> selectPage(Page<Admin> userPage, AdminQueryVo adminQueryVo);
}

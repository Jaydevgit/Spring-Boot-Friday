package com.example.demo.service.impl;

import com.example.demo.base.result.Results;
import com.example.demo.dao.RoleUserDao;
import com.example.demo.model.SysRoleUser;
import com.example.demo.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Jay
 * Date: 2019/12/21 19:54
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    private RoleUserDao roleUserDao;

    @Override
    public Results getSysRoleUserByUserId(Integer userId) {
        SysRoleUser sysRoleUser=roleUserDao.getSysRoleUserByUserId(userId);
        /*判断是否有sysRoleUser这个值*/
        if (sysRoleUser!=null){
            return Results.success(sysRoleUser);
        }else {
            return Results.success();
        }

    }
}

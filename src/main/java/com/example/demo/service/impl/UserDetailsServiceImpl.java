package com.example.demo.service.impl;

import com.example.demo.dao.PermissionDao;
import com.example.demo.dto.LoginUser;
import com.example.demo.model.SysPermission;
import com.example.demo.model.SysUser;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Jay
 * Date: 2019/12/31 16:09
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ///查到sysUser对象
        SysUser sysUser=userService.getUser(username);
        if (sysUser==null){
            throw new AuthenticationCredentialsNotFoundException("用户名不存在");
        }else if (sysUser.getStatus()==SysUser.Status.LOCKED){
            throw new LockedException("用户被锁定，请联系管理员");
        }else if (sysUser.getStatus()==SysUser.Status.DISABLED){
            throw new LockedException("用户被锁定，请联系管理员");
        }

        LoginUser loginUser=new LoginUser();
        BeanUtils.copyProperties(sysUser,loginUser);

        List<SysPermission> permissions=permissionDao.listByUserId(sysUser.getId());
        loginUser.setPermissions(permissions);

        return loginUser;
    }
}

package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.base.result.Results;
import com.example.demo.dao.PermissionDao;
import com.example.demo.dao.RolePermissionDao;
import com.example.demo.model.SysPermission;
import com.example.demo.service.PermissionService;
import com.example.demo.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Jay
 * Date: 2019/12/27 10:07
 */
@Service
@Transactional
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;


    @Override
    public Results<JSONArray> listAllPermission() {
        log.info(getClass().getName() + ".listAllPermission()");
        List datas = permissionDao.findAll();
        JSONArray array = new JSONArray();
        log.info(getClass().getName() + ".setPermissionsTree(?,?,?)");
        TreeUtils.setPermissionsTree(0, datas, array);/*做一个递归调用*/
        return Results.success(array);
    }


    @Override
    public Results<SysPermission> listByRoleId(Integer roleId) {
        return Results.success(0, permissionDao.listByRoleId(roleId));
    }

    @Override
    public Results<SysPermission> getMenuAll() {
        return Results.success(0,permissionDao.findAll());
    }

    @Override
    public Results save(SysPermission sysPermission) {
        return permissionDao.save(sysPermission)>0?Results.success():Results.failure();
    }

    @Override
    public SysPermission getSysPermissionById(Integer id) {
        return permissionDao.getSysPermissionById(id);
    }

    @Override
    public Results updateSysPermission(SysPermission sysPermission) {
        return (permissionDao.update(sysPermission)>0)?Results.success():Results.failure();
    }

    @Override
    public Results delete(Integer id) {
        permissionDao.deleteById(id);
        permissionDao.deleteByParentId(id);
        return Results.success();
    }

    @Override
    public Results getMenu(Long userId) {
        List<SysPermission> datas = permissionDao.listByUserId(userId);
        datas.stream().filter(p->p.getType().equals(1)).collect(Collectors.toList());
        JSONArray array = new JSONArray();
        TreeUtils.setPermissionsTree(0, datas, array);/*做一个递归调用*/
        return Results.success(array);
    }
}

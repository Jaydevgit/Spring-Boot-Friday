package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.base.result.Results;
import com.example.demo.model.SysPermission;

/**
 * Author: Jay
 * Date: 2019/12/27 10:06
 */
public interface PermissionService {
    Results<JSONArray> listAllPermission();

    Results<SysPermission> listByRoleId(Integer intValue);

    Results<SysPermission> getMenuAll();

    Results<SysPermission> save(SysPermission permission);

    SysPermission getSysPermissionById(Integer id);

    Results updateSysPermission(SysPermission sysPermission);

    Results delete(Integer id);

    Results<SysPermission> getMenu(Long userId);
}

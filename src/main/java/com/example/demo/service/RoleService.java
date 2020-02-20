package com.example.demo.service;

import com.example.demo.base.result.Results;
import com.example.demo.dto.RoleDto;
import com.example.demo.model.SysRole;
import com.example.demo.model.SysUser;

public interface RoleService {


    Results<SysRole> getAllRoles();

    Results<SysRole> getAllRolesByPage(Integer offset, Integer limit);

    Results<SysRole> getRolesByFuzzyRoleNamePage(String roleName, Integer offset, Integer limit);

    Results<SysRole> save(RoleDto roleDto);

    int update(RoleDto roleDto);

    SysRole getRoleById(Integer id);

    Results delete(Integer id);
}

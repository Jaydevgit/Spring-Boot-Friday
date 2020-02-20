package com.example.demo.dto;

import com.example.demo.model.SysRole;

import java.util.List;

/**
 * Author: Jay
 * Date: 2019/12/27 10:09
 */
public class RoleDto extends SysRole {
    private static final long serialVersionUID = -5784234789156935003L;

    private List<Long> permissionIds;

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}

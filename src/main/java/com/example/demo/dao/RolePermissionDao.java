package com.example.demo.dao;

import com.example.demo.model.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: Jay
 * Date: 2019/12/13 10:29
 */
@Mapper
public interface RolePermissionDao {

    @Delete("delete from sys_role_permission where permissionId=#{permissionId}")
    int delete(RolePermission rolePermission);

    /*进行批量插入*/
    int save(@Param("roleId")Integer id, @Param("permissionIds") List<Long> permissionIds);

    @Delete("delete from sys_role_permission where roleId=#{roleId}")
    int deleteRolePermission(Integer roleId);
}

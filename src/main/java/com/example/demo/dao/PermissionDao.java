package com.example.demo.dao;

import com.example.demo.base.result.Results;
import com.example.demo.model.SysPermission;
import com.example.demo.model.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Author: Jay
 * Date: 2019/12/13 10:28
 */
@Mapper
public interface PermissionDao {
    int update(SysPermission e);

    @Select("select * from sys_permission t")
    List<SysPermission> findAll();

    /*inner join是内连接，通过on来对两张表进行关联*/
    @Select("select p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId where rp.roleId = #{roleId} order by p.sort")
    List<SysPermission> listByRoleId(Integer roleId);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into sys_permission(parentId,name,css,href,type,permission,sort) values(#{parentId},#{name},#{css},#{href},#{type},#{permission},#{sort})")
    int save(SysPermission sysPermission);

    @Select("select * from sys_permission t where t.id =#{id}")
    SysPermission getSysPermissionById(Integer id);

    @Delete("delete from sys_permission where id =#{id}")
    int deleteById(Integer id);

    @Delete("delete from sys_permission where parentId=#{parentId}")
    int deleteByParentId(Integer parentId);

    @Select("select sp.* from sys_role_user sru " +
            "inner join sys_role_permission srp on srp.roleId = sru.roleId " +
            "left join sys_permission sp on srp.permissionId = sp.id " +
            "where sru.roleId = #{userId}")
    List<SysPermission> listByUserId(@Param("userId") Long userId);
}

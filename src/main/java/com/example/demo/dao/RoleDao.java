package com.example.demo.dao;

import com.example.demo.dto.RoleDto;
import com.example.demo.model.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Jay
 * Date: 2019/12/13 10:28
 */
@Mapper
public interface RoleDao {

    @Select("select * from sys_role t")
    List<SysRole> getAllRoles();

    @Select("select count(*) from sys_role t")
    Long countAllRoles();

    @Select("select * from sys_role t limit #{startPosition},#{limit}")
    List<SysRole> getAllRolesByPage(@Param("startPosition") Integer startPosition,@Param("limit") Integer limit);

    @Select("select count(*) from sys_role t where t.name like '%${roleName}%'")
    Long countRoleByFuzzyRoleName(@Param("roleName") String roleName);

    @Select("select * from sys_role t where t.name like '%${roleName}%' limit #{startPosition},#{limit}")
    List<SysRole> getRoleByFuzzyRoleNamePage(@Param("roleName") String roleName,@Param("startPosition") Integer startPosition,@Param("limit") Integer limit);

    int saveRole(SysRole role);

    int update(SysRole role);

    @Select("select * from sys_role t where t.id=#{id}")
    SysRole getById(Integer id);

    @Delete("delete from sys_role where id=#{id}")
    int delete(Integer id);
}

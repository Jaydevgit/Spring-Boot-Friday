package com.example.demo.dao;

import com.example.demo.model.SysRoleUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: Jay
 * Date: 2019/12/13 10:28
 */
@Mapper
public interface RoleUserDao {
    @Insert("insert into sys_role_user(userId, roleId) values(#{userId}, #{roleId})")
    int save(SysRoleUser sysRoleUser);

    int updateSysRoleUser(SysRoleUser sysRoleUser);

    @Select("select * from sys_role_user t where t.userId=#{userId}")
    SysRoleUser getSysRoleUserByUserId(Integer userId);

    @Delete("delete from sys_role_user where userId=#{userId}")
    int deleteRoleUserByUserId(int intValue);

    @Select("select * from sys_role_user t where t.roleId = #{roleId}")
    List<SysRoleUser> listAllSysRoleUserByRoleId(Integer id);
}

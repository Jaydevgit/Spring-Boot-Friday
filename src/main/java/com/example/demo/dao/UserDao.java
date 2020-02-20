package com.example.demo.dao;

import com.example.demo.dto.UserDto;
import com.example.demo.model.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*注意加Mapper注解*/
@Mapper
public interface UserDao {

	@Select("select * from sys_user t where t.username = #{username}")
	SysUser getUser(String username);

	@Select("select * from sys_user t order by t.id limit #{startPosition},#{limit}")
	List<SysUser> getAllUsersByPage(@Param("startPosition")Integer startPosition,@Param("limit")Integer limit);

	/*总记录条数*/
	@Select("SELECT count(*) FROM `sys_user` t")
	Long countAllUsers();

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_user(username, password, nickname, headImgUrl, phone, telephone, email, birthday, sex, status, createTime, updateTime) values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{telephone}, #{email}, #{birthday}, #{sex}, #{status}, now(), now())")
	int save(SysUser user);

	int updateUser(SysUser user);

	@Select("select * from sys_user t where t.telephone=#{telephone}")
    SysUser getUserByPhone(String telephone);

	@Select("select * from sys_user t where t.id=#{id}")
	SysUser getUserById(Long id);

	@Delete("delete from sys_user where id=#{id}")
	int deleteUser(int userId);


	@Select("SELECT count(*) FROM sys_user t where t.username like '%{username}%'")
    Long getUserByFuzzyUserName(@Param("username") String username);

	/*返回结果集*/
	@Select("select * from sys_user t where t.username like '%{username}%' limit #{startPosition},#{limit}")
	List<SysUser> getUserByFuzzyUserNamePage(@Param("username") String username,@Param("startPosition") Integer startPosition,@Param("limit") Integer limit);
}

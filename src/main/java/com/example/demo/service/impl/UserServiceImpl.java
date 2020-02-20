package com.example.demo.service.impl;

import com.example.demo.base.result.Results;
import com.example.demo.dao.RoleUserDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserDto;
import com.example.demo.model.SysRoleUser;
import com.example.demo.model.SysUser;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  /*事务控制*/
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleUserDao roleUserDao;

	@Override
	public SysUser getUser(String username){
		return userDao.getUser(username);
	}

	@Override
	public Results<SysUser> getAllUsersByPage(Integer offset, Integer limit) {
		//返回count和user-list
		return Results.success(userDao.countAllUsers().intValue(),userDao.getAllUsersByPage(offset,limit));

	}

	@Override
	public Results<SysUser> save(SysUser user, Integer roleId) {
		if (roleId!=null){
			/*user*/
			userDao.save(user);
			/*roleUser*/
			SysRoleUser sysRoleUser=new SysRoleUser();
			sysRoleUser.setRoleId(roleId);
			sysRoleUser.setUserId(user.getId().intValue());
			roleUserDao.save(sysRoleUser);
			return Results.success();
		}
		return Results.failure();
	}

	@Override
	public SysUser getUserByPhone(String telephone) {
		return userDao.getUserByPhone(telephone);
	}

	@Override
	public SysUser getUserById(Long id) {
		return userDao.getUserById(id);
	}

	@Override
	public Results<SysUser> updateUser(UserDto userDto, Integer roleId) {
		/*判断roleId是否为空*/
		if (roleId!=null){
			//sysUser表更新
			userDao.updateUser(userDto);
			//sysRoleUser表  若存在数据则更新update 不存在则保存save
			SysRoleUser sysRoleUser=new SysRoleUser();
			sysRoleUser.setUserId(userDto.getId().intValue());
			sysRoleUser.setRoleId(roleId);
			if (roleUserDao.getSysRoleUserByUserId(userDto.getId().intValue()) !=null){
				roleUserDao.updateSysRoleUser(sysRoleUser);
			}else {
				roleUserDao.save(sysRoleUser);
			}
			return Results.success();
		}else {
			return Results.failure();
		}

	}

	@Override
	public int deleteUser(Long id) {
		//删除sysroleuser
		roleUserDao.deleteRoleUserByUserId(id.intValue());
		//删除sysuser
		return userDao.deleteUser(id.intValue());
	}

	@Override
	public Results<SysUser> getUserByFuzzyUserName(String username, Integer startPosition, Integer limit) {
		return Results.success(userDao.getUserByFuzzyUserName(username).intValue(),userDao.getUserByFuzzyUserNamePage(username,startPosition,limit));
	}
}

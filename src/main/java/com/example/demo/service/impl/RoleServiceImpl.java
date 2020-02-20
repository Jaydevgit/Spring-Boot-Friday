package com.example.demo.service.impl;

import com.example.demo.base.result.ResponseCode;
import com.example.demo.base.result.Results;
import com.example.demo.dao.RoleDao;
import com.example.demo.dao.RolePermissionDao;
import com.example.demo.dao.RoleUserDao;
import com.example.demo.dto.RoleDto;
import com.example.demo.model.SysRole;
import com.example.demo.model.SysRoleUser;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleUserDao roleUserDao;

	@Autowired
	RolePermissionDao rolePermissionDao;

	@Autowired
	private RoleDao roleDao;

	@Override
	public Results<SysRole> getAllRoles() {
		return Results.success(50,roleDao.getAllRoles());
	}

	@Override
	public Results<SysRole> getAllRolesByPage(Integer offset, Integer limit) {
		return Results.success(roleDao.countAllRoles().intValue(),roleDao.getAllRolesByPage(offset,limit));
	}

	@Override
	public Results<SysRole> getRolesByFuzzyRoleNamePage(String roleName, Integer startPosition, Integer limit) {
		return Results.success(roleDao.countRoleByFuzzyRoleName(roleName).intValue(),roleDao.getRoleByFuzzyRoleNamePage(roleName,startPosition,limit));
	}

	@Override
	public Results<SysRole> save(RoleDto roleDto) {
		//1、先保存角色"
		roleDao.saveRole(roleDto);
		List<Long> permissionIds = roleDto.getPermissionIds();
		//移除0,permission id是从1开始
		permissionIds.remove(0L);
		//2、保存角色对应的所有权限，进行批量插入的操作
		if (!CollectionUtils.isEmpty(permissionIds)) {
			rolePermissionDao.save(roleDto.getId(), permissionIds);
		}
		return Results.success();
	}

	@Override
	public int update(RoleDto roleDto) {
		List<Long> permissionIds=roleDto.getPermissionIds();
		/*移除根目录的0*/
		permissionIds.remove(0L);
		/*1.更新角色之前要删除该角色之前的所有权限*/
		rolePermissionDao.deleteRolePermission(roleDto.getId());
		/*2.判断该角色是否有赋予权限值，有就添加*/
		if (!CollectionUtils.isEmpty(permissionIds)){
			rolePermissionDao.save(roleDto.getId(),permissionIds);
		}
		return roleDao.update(roleDto);
	}

	@Override
	public SysRole getRoleById(Integer id) {
		return roleDao.getById(id);
	}

	@Override
	public Results delete(Integer id) {
	    /*根据role查所有user的关联*/
		List<SysRoleUser> datas=roleUserDao.listAllSysRoleUserByRoleId(id);
		if (datas.size()<=0){
		    roleDao.delete(id);
		    return Results.success();
        }
		return Results.failure(ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getCode(),ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getMessage());
	}
}

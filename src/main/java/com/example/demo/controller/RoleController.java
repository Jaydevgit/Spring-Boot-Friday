package com.example.demo.controller;

import com.example.demo.base.result.PageTableRequest;
import com.example.demo.base.result.Results;
import com.example.demo.dto.RoleDto;
import com.example.demo.model.SysRole;
import com.example.demo.model.SysUser;
import com.example.demo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.PublicKey;

/**
 * Author: Jay
 * Date: 2019/12/19 14:58
 */
@Controller
@RequestMapping("role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    @ResponseBody
    public Results<SysRole> getAll(){
        log.info("RoleController.getAll()");
        return roleService.getAllRoles();
    }

    @GetMapping("/list")
    @ResponseBody
    public Results list(PageTableRequest request){
        log.info("RoleController.list(): param ( request = "+request+")");
        request.countOffset();
        return roleService.getAllRolesByPage(request.getOffset(),request.getLimit());
    }


    @GetMapping("/findUserByFuzzyRoleName")
    @ResponseBody
    public Results findUserByFuzzyRoleName(PageTableRequest request,String roleName){
        request.countOffset();
        return roleService.getRolesByFuzzyRoleNamePage(roleName,request.getOffset(),request.getLimit());
    }

    @GetMapping(value = "/add")
    public String addRole(Model model){
        model.addAttribute("sysRole",new SysRole());
        return "role/role-add";
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public Results saveRole(RoleDto roleDto){
        return roleService.save(roleDto);
    }

    @GetMapping(value = "/edit")
    public String editRole(Model model,SysRole role){
        model.addAttribute("sysRole",roleService.getRoleById(role.getId()));
        return "role/role-edit";
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public Results<SysRole> updateRole(RoleDto roleDto){
        int a=roleService.update(roleDto);
        return Results.success();
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    public Results<SysRole> deleteRole(RoleDto roleDto){
        return roleService.delete(roleDto.getId());
    }

}

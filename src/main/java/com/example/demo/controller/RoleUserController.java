package com.example.demo.controller;

import com.example.demo.base.result.ResponseCode;
import com.example.demo.base.result.Results;
import com.example.demo.dto.UserDto;
import com.example.demo.model.SysUser;
import com.example.demo.service.RoleUserService;
import com.example.demo.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Jay
 * Date: 2019/12/21 19:48
 */
@RestController
@RequestMapping("roleuser")
@Slf4j
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;

    @PostMapping("/getRoleUserByUserId")
    public Results getRoleUserByUserId( Integer userId){
        log.info("RoleUserController.getRoleUserByUserId： param="+userId);
        return roleUserService.getSysRoleUserByUserId(userId);
    }
}

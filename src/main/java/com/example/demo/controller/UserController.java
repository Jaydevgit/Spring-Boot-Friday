package com.example.demo.controller;

import com.example.demo.base.result.PageTableRequest;
import com.example.demo.base.result.ResponseCode;
import com.example.demo.base.result.Results;
import com.example.demo.dto.UserDto;
import com.example.demo.model.SysUser;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Jay
 * Date: 2019/12/10 17:07
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    @ResponseBody
    public SysUser user(@PathVariable String username){
        log.info("UserController.user（）:param（username="+username+"）");
        return userService.getUser(username);
    }

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
    public Results<SysUser> getUsers(PageTableRequest request){
        log.info("UserController.getUsers():param(request="+request+")");
        request.countOffset();
        return userService.getAllUsersByPage(request.getOffset(),request.getLimit());
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public String addUser(Model model){
        model.addAttribute(new SysUser());
        return "user/user-add";
    }

    //做真正的处理，编写新增功能
    @PostMapping("/add") /*接收从html传过来的数据*/
    @ResponseBody
    public Results<SysUser> saveUser(UserDto userDto,Integer roleId){
        SysUser sysUser=null;
        sysUser=userService.getUserByPhone(userDto.getTelephone());
        if (sysUser!=null&&sysUser.getId().equals(userDto.getId())){
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());
        }

        userDto.setStatus(1);
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        //userDto.setPassword(MD5.crypt(userDto.getPassword()));
        return userService.save(userDto,roleId);
    }

    /*String pattern="yyyy-MM-dd";
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request){
        binder.registerCustomEditor(Data.class,new CustomDateEditor(new SimpleDateFormat(pattern),true));
    }*/

    @GetMapping("/edit")
    public String editUser(Model model,SysUser sysUser){
        model.addAttribute(userService.getUserById(sysUser.getId()));
        return "user/user-edit";
    }


    @PostMapping("/edit")
    @ResponseBody
    public Results<SysUser> updateUser(UserDto userDto,Integer roleId){
        SysUser sysUser=null;
        /*验证电话号码唯一*/
        sysUser=userService.getUserByPhone(userDto.getTelephone());
        if (sysUser!=null&&sysUser.getId().equals(userDto.getId())){
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());
        }

        return userService.updateUser(userDto,roleId);
    }

    @GetMapping("/delete")
    @ResponseBody
    public Results<Object> deleteUser(UserDto userDto){
        int count=userService.deleteUser(userDto.getId());
        if (count>0){
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    @GetMapping("/findUserByFuzzyUserName")
    @ResponseBody
    public Results<SysUser> findUserByFuzzyUserName(PageTableRequest request,String username){
        log.info("UserController.findUserByFuzzyUsername():param(request1="+request+"，username="+username+")");
        request.countOffset();
        return userService.getUserByFuzzyUserName(username,request.getOffset(),request.getLimit());
    }

}

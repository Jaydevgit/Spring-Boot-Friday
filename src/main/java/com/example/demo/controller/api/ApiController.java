package com.example.demo.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author: Jay
 * Date: 2019/12/11 13:33
 */
@Controller
@RequestMapping("${api-url}")
public class ApiController {

    @RequestMapping("/getPage")
    @ResponseBody
    public ModelAndView getPage(ModelAndView modelAndView, String pageName){
        modelAndView.setViewName(pageName);  /*pageName就是对应的user-list.html文件名*/
        return modelAndView;
    }
}

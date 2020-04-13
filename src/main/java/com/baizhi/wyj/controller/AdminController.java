package com.baizhi.wyj.controller;

import com.baizhi.wyj.entity.Admin;
import com.baizhi.wyj.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RequestMapping("Admin")
@Controller
public class AdminController {
    @Resource
    AdminService adminService;

    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,String> login(Admin admin, String code){
        System.out.println("admin = " + admin);
        System.out.println("code = " + code);
        HashMap<String, String> map = adminService.login(admin, code);
        return map;
    }
    @RequestMapping("loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
}

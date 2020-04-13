package com.baizhi.wyj.controller;

import com.baizhi.wyj.entity.Admin;
import com.baizhi.wyj.service.AdminService;
import com.baizhi.wyj.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("log")
@RestController
public class LogController {
    @Resource
    LogService logService;

    /**
     * 分页查询所有数据
     * rows:每页展示的数据条数
     * page：当前页
     */

    @RequestMapping("findAllByLimit")
    public Map<String ,Object> findAllByLimit(Integer rows, Integer page) {
        return logService.queryAllByLimit(rows,page);
    }
}

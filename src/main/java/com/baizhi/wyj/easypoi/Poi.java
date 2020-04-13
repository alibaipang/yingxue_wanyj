package com.baizhi.wyj.easypoi;

import com.baizhi.wyj.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("poi")
@RestController
public class Poi {
    @Resource
    UserService userService;


    /**
     * 导出用户的数据信息
     * */

    @RequestMapping("poi")
    public void pio(){
        userService.poi();
    }
}


package com.baizhi.wyj.echarts;

import com.alibaba.fastjson.JSON;
import com.baizhi.wyj.service.UserService;
import com.baizhi.wyj.vo.Mondel;
import io.goeasy.GoEasy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("echarts")
public class Echarts {
    @Resource
    UserService userService;
    /**
     * 根据性别、查看用户每月注册的情况
     * */
    @RequestMapping("queryUserByMonth")
    public HashMap<String,Object> queryUserByMonth(){
        HashMap<String, Object> hashMap = userService.queryUserByMonth();
        String content = JSON.toJSONString(hashMap);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-7f40d4e429584d36a6c6806352b81183");
        goEasy.publish("yingx_ali", content);
        return hashMap;
    }
    /**
     * 根据用户的性别查询用户的分布情况
     * */
    @RequestMapping("queryUserByAddress")
    public List<Mondel> queryUserByAddress(){
        List<Mondel> list = userService.queryUserByAddress();
        for (Mondel mondel : list) {
            System.out.println("mondel = " + mondel);
        }
        return list;
    }

}

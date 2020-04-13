package com.baizhi.wyj.controller;

import com.baizhi.wyj.entity.User;
import com.baizhi.wyj.service.UserService;
import com.baizhi.wyj.util.AliyunShortMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequestMapping("user")
@RestController
public class UserController {
    @Resource
    UserService userService;


    /**
     * 分页查询所有数据
     * rows:每页展示的数据条数
     * page：当前页
     */


    @RequestMapping("findAllByLimit")
    public Map<String ,Object> findAllByLimit(Integer rows, Integer page) {
        return userService.queryAllByLimit(rows,page);
    }


    /**
     * 用户的编辑
     * oper 的值为add  edit del
     * 将添加、修改的id一json的形式返回给页面
     * */


    @RequestMapping("edit")
    public String edit(User user,String oper,String[] id ){
        String uid = userService.edit(user, oper, id);
        return uid;
    }


    /**
     * 文件上传
     * 1、根据相对路径获取绝对的路径
     * 2、创建文件夹
     * 3、获取文件名、生成时间戳
     * 4、文件上传
     * */


    @RequestMapping("fileupload")
    public Integer fileupload(MultipartFile headImg, String id,HttpServletRequest request) {
        userService.fileupload(headImg,id,request);
        return 12;
    }

    /**
     * 将文件上传到阿里云
     * */


    @RequestMapping("fileUpLoadAliYun")
    public Integer fileUpLoadAliYun(MultipartFile headImg, String id) {
        userService.fileUpLoadAliYun(headImg,id);
        return 12;
    }


    /**
     * 修改用户的状态
     * 1代表正常
     * 2代表冻结
     * */


    @RequestMapping("update")
    public Integer update(User user,String id,String status) {
        userService.update(user,id,status);
        return 12;
    }

    /**
     *发送手机验证码
     * @param phoneNumber  手机号 */


@RequestMapping("sendPhone")
    public String sendPhone(String phoneNumber) {
        System.out.println("phoneNumber = " + phoneNumber);
        return AliyunShortMessage.getMessage(phoneNumber);
    }
}


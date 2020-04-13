package com.baizhi.wyj.service;


import com.baizhi.wyj.entity.User;
import com.baizhi.wyj.vo.Mondel;
import com.baizhi.wyj.vo.UserVo;
import com.baizhi.wyj.vo.UserVo2;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 后台的方法
     */

    Map<String ,Object> queryAllByLimit(Integer rows, Integer page);
    void fileupload(MultipartFile headImg, String id, HttpServletRequest request);
    void fileUpLoadAliYun(MultipartFile headImg, String id);
    void update(User user,String id,String status);
    String edit(User user,String oper,String[] id );
    String sendPhone(String phoneNumber);

     /**
      *  导出用户的数据方法
      *  */

    void poi();

      /**
       * 前台用户的注册
       * */

    UserVo login(User user);
    /**
     * 根据性别、查看用户每月注册的情况
     * */
    HashMap<String,Object> queryUserByMonth();
    /**
     * 根据用户的性别查询用户的分布情况
     * */
    List<Mondel>queryUserByAddress();
}

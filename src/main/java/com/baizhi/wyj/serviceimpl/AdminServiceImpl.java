package com.baizhi.wyj.serviceimpl;

import com.baizhi.wyj.entity.Admin;
import com.baizhi.wyj.entity.AdminExample;
import com.baizhi.wyj.mapper.AdminMapper;
import com.baizhi.wyj.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper adminMapper;
    @Resource
    HttpSession session;

    @Override
    public HashMap<String,String> login(Admin admin, String code) {
        System.out.println("admin = " + admin);
        HashMap<String, String> map = new HashMap<>();
        //获取session中的验证码
        String image = (String) session.getAttribute("imagecode");
        //判断验证码是否存在
        if (image.equals(code)) {
            //判断管理员是否存在
            AdminExample adminExample = new AdminExample();
            adminExample.createCriteria().andUsernameEqualTo(admin.getUsername());
            List<Admin> admins = adminMapper.selectByExample(adminExample);
            if (admins.size() != 0) {
                for (Admin admin1 : admins) {
                    //判断管理员密码是否正确
                    if (admin1.getPassword().equals(admin.getPassword())) {
                        session.setAttribute("admin", admin1);
                        map.put("status","200");
                        map.put("message","登录成功");
                    } else {
                        map.put("status","400");
                        map.put("message","密码错误");
                    }
                }
            } else {
                map.put("status","400");
                map.put("message","用户名不存在");
            }

        } else {
            map.put("status","400");
            map.put("message","验证码错误");
        }
        return map;
    }

}

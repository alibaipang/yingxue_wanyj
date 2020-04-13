
package com.baizhi.wyj.serviceimpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.wyj.annotation.AddCache;
import com.baizhi.wyj.annotation.DelCahe;
import com.baizhi.wyj.annotation.LogAnnotation;
import com.baizhi.wyj.entity.User;
import com.baizhi.wyj.entity.UserExample;
import com.baizhi.wyj.mapper.UserMapper;
import com.baizhi.wyj.service.UserService;
import com.baizhi.wyj.util.AliyunOssUtil;
import com.baizhi.wyj.util.AliyunShortMessage;
import com.baizhi.wyj.util.DownLoadUtil;
import com.baizhi.wyj.util.UUIDUtil;
import com.baizhi.wyj.vo.Mondel;
import com.baizhi.wyj.vo.UserAddress;
import com.baizhi.wyj.vo.UserVo;
import com.baizhi.wyj.vo.UserVo2;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    /**
     * 分页查询用户的数据
     * records 总条数
     * total（总条数除以每页展示的条数  是否有余数）   总页数
     * page  当前页
     * rows   数据*/

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AddCache
    public Map<String, Object> queryAllByLimit(Integer rows, Integer page) {
        //分页查询、忽略几条、获取几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> users = userMapper.selectByExampleAndRowBounds(new UserExample(), rowBounds);
        //查询总条数、计算总页数
        Integer records = userMapper.selectCount(new User());
        Integer total = records%rows==0?records/rows:records/rows+1;
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",users);
        map.put("total",total);
        map.put("records",records);
        map.put("page",page);
        return map;
    }
    /**
     * 文件上传
     * 根据相对路径获取绝对路径
     * 创建文件夹
     * 获取文件名
     * 执行文件上传
     * 修改user的属性*/

    @Override
    @AddCache
    public void fileupload(MultipartFile headImg, String id,HttpServletRequest request) {
        System.out.println("id = " + id);
        //根据相对路径获取绝对的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/image");
        File file = new File(realPath);
        //创建文件夹
        if(!file.exists()){
            file.mkdirs();
        }
        //获取文件名、生成时间戳
        String originalFilename = headImg.getOriginalFilename();
        String fileName = System.currentTimeMillis()+"-"+originalFilename;
        //文件上传
        try {
            headImg.transferTo(new File(realPath,fileName));
            //图片的修改
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(id);
            User user = new User();
            user.setHeadImg(fileName);
            //修改
            userMapper.updateByPrimaryKeySelective(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 将图片上传到阿里云*/

    @Override
    @AddCache
    public void fileUpLoadAliYun(MultipartFile headImg, String id) {
        //获取文件名、给文件名拼接上一个时间戳
        String filename = headImg.getOriginalFilename();
        String newName = System.currentTimeMillis()+"-"+filename;
        //拼接目录
        String newNames = "images/"+newName;
        //将文件上传到阿里云
        AliyunOssUtil.uploadFileBytes(headImg,newNames);
        //修改用户的image属性
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);
        User user = new User();
        user.setHeadImg("https://yingx-ali.oss-cn-beijing.aliyuncs.com/images/"+newName);
        userMapper.updateByExampleSelective(user,example);
    }
    /**
     * 修改用户的状态、1为激活、2为冻结*/

    @LogAnnotation(name = "修改用户的状态")
    @Override
    @DelCahe
    public void update(User user,String id,String status) {
        user.setId(id);
        if ("1".equals(status)){
            user.setStatus("2");
        }else {
            user.setStatus("1");
        }
        userMapper.updateByPrimaryKeySelective(user);
    }
    /**
     * 编辑用户的信息
     * 添加(add)、修改(edit)、删除(del)
     * 注意删除的参数名必须是id、通用mapper的话必须实现循环删除
     * */

    @LogAnnotation(name = "用户的编辑操作")
    @Override
    @DelCahe
    public String edit(User user, String oper, String[] id) {
        String uuid = null;
        if (("add").equals(oper)){
            uuid = UUIDUtil.getUUID();
            user.setId(uuid);
            user.setStatus("1");
            user.setCreateDate(new Date());
            userMapper.insertSelective(user);
            return uuid;
        }else if(("edit").equals(oper)){
            UserExample example = new UserExample();
            uuid = user.getId();
            example.createCriteria().andIdEqualTo(uuid);
            userMapper.updateByExampleSelective(user, example);
            return uuid;
        }else if (("del").equals(oper)){
            if (id.length!=0){
                for (String ids : id) {
                    userMapper.deleteByPrimaryKey(ids);
                }
            }
        }
        return uuid;
    }

    /**
     *发送手机验证码
     * @param phoneNumber  手机号 */


    @Override
    public String sendPhone(String phoneNumber) {
        return AliyunShortMessage.getMessage(phoneNumber);
    }

    /**
     * 使用easyPoi导出用户的数据
     * */

    @Override
    public void poi() {
        //查询数据信息
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            String headImg = user.getHeadImg();
            //将图片下载保存在本地
            DownLoadUtil.download(headImg);
            String[] split = headImg.split("/");
            String fileName = split[split.length-1];
            //设置图片的绝对路径
            user.setHeadImg("D:/Download/"+fileName);
        }
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook  workbook = ExcelExportUtil.exportExcel(new ExportParams("用户的数据信息","数据信息1"),User.class, users);
        //导出表格
        try {
            workbook.write(new FileOutputStream(new File("D://用户数据.xls")));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 前台用户的注册
     * */
    @Override
    @DelCahe
    public UserVo login(User user) {
        user.setId(UUIDUtil.getUUID());
        user.setStatus("1");
        user.setCreateDate(new Date());
        userMapper.insertSelective(user);
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setName(user.getUsername());
        return userVo;
    }
    /**
     * 根据性别、查看用户每月注册的情况
     * */
    @Override
    @AddCache
    public HashMap<String,Object> queryUserByMonth() {
        HashMap<String, Object> map = new HashMap<>();
        //查询数据库的数据
        List<UserVo2> userVo2s = userMapper.queryUserByMonth("M");
        List<UserVo2> userVo2s2 = userMapper.queryUserByMonth("F");
        //获取返回的数据
        ArrayList<Object> list1 = new ArrayList<>();
        ArrayList<Object> list2 = new ArrayList<>();
        ArrayList<Object> list3 = new ArrayList<>();
        for (UserVo2 userVo2 : userVo2s) {
            list1.add(userVo2.getReleaseMonth());
            list2.add(userVo2.getNumber());
        }
        for (UserVo2 userVo2 : userVo2s2) {
            list3.add(userVo2.getNumber());
        }
        //设置返回的map的属性、以及属性值
        map.put("month",list1);
        map.put("man",list3);
        map.put("woman",list2);
        return map;
    }
    /**
     * 根据用户的性别查询用户的分布情况
     * */
    @Override
    @AddCache
    public List<Mondel> queryUserByAddress() {
        //获取数据库的信息
        List<UserAddress> manList = userMapper.queryUserByAddress("F");
        List<UserAddress> womanList = userMapper.queryUserByAddress("M");
        //创建返回的集合
        ArrayList<Mondel> list = new ArrayList<>();
        //设置返回的数据
        Mondel manMondel = new Mondel("man",manList);
        Mondel womanMondel = new Mondel("woman",womanList);
        list.add(manMondel);
        list.add(womanMondel);
        return list;
    }
}


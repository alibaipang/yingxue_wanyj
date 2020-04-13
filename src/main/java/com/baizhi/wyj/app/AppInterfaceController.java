package com.baizhi.wyj.app;

import com.baizhi.wyj.entity.User;
import com.baizhi.wyj.service.CategoryService;
import com.baizhi.wyj.service.UserService;
import com.baizhi.wyj.service.VideoService;
import com.baizhi.wyj.util.AliyunShortMessage;
import com.baizhi.wyj.vo.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("app")
public class AppInterfaceController {

    @Resource
    VideoService videoService;
    @Resource
    CategoryService categoryService;
    @Resource
    UserService userService;

    /**
     *前台接口的方法
     *  */



    /**
     *首页展示的方法
     *  */

    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime() {
        try {
            List<VideoVo> videoVos = videoService.queryByReleaseTime();
            return new CommonResult().success(videoVos);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }

    /**
     *类别展示的方法
     *  */

    @RequestMapping("queryAllCategory")
    public CommonResult queryAllCategory() {
        try {
            List<CategoryVo> categoryVos = categoryService.queryAllCategory();
            return new CommonResult().success(categoryVos);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }

    /**
     *二级类别下的视频
     *  */

    @RequestMapping("queryCateVideoList")
    public CommonResult queryCateVideoList(String cateId) {
        try {
            List<VideoVo2> videoVo2s = categoryService.queryCateVideoList(cateId);
            return new CommonResult().success(videoVo2s);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }

    /**
     * 用户发送验证码
     * */

    @RequestMapping("getPhoneCode")
    public CommonResult getPhoneCode(String phone) {
        String message = AliyunShortMessage.getMessage(phone);
        if ("发送成功".equals(message)){
            return new CommonResult().success(phone);
        }else{
            return new CommonResult().failed(message);
        }
    }

    /**
     * 用户的注册
     * */
    /**
     * 用户发送验证码
     * */

    @RequestMapping("login")
    public CommonResult login(User user) {
        try {
            UserVo login = userService.login(user);
            return new CommonResult().success(login);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }

    /**
     * 视频的详情
     * */

    @RequestMapping("queryByVideoDetail")
    public CommonResult queryByVideoDetail(String videoId,String cateId, String userId) {
        try {
            VideoVo3 videoVo3 = videoService.queryByVideoDetail(videoId, cateId, userId);
            return new CommonResult().success(videoVo3);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }

    /**
     * 视频的详情
     * */

    @RequestMapping("queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content) {
        try {
            List<VideoVo2> videoVo2s = videoService.queryByLikeVideoName(content);
            return new CommonResult().success(videoVo2s);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().failed();
        }
    }
}

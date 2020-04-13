package com.baizhi.wyj.service;

import com.baizhi.wyj.entity.Video;
import com.baizhi.wyj.vo.VideoVo;
import com.baizhi.wyj.vo.VideoVo2;
import com.baizhi.wyj.vo.VideoVo3;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoService {
    //后台的展示所用的方法

    Map<String ,Object> queryAllByLimit(Integer rows, Integer page);
    //后台的编辑所用的方法

    String edit(Video video, String oper, String[] id );
    //后台的视频上传所用的方法

    void fileUpLoadAliYun(MultipartFile cover, String id);

    //前台的数据展示

    List<VideoVo> queryByReleaseTime();
    /**
     * 视频详情*/
    VideoVo3 queryByVideoDetail(String videoId, String userId,String cateId);
    /**
     * 模糊查询视频的信息
     * */
    List<VideoVo2> queryByLikeVideoName(String content);
    /**
     * 添加视频
     * */
    VideoVo2 save();
    /**
     * ES的的检索
     * */
    List<Video> querySearch(String content);
    /**
     * 视频的信息的高亮检索
     * */
    List<Video> higHlightSearch(String content);
}

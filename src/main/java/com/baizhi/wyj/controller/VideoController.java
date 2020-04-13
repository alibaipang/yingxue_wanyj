package com.baizhi.wyj.controller;

import com.baizhi.wyj.entity.Video;
import com.baizhi.wyj.service.VideoService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RequestMapping("video")
@RestController
public class VideoController {
    @Resource
    VideoService videoService;
    /**
     *后台的方法
     * */

    /**
     * 分页查询所有数据
     * rows:每页展示的数据条数
     * page：当前页
     */

    @RequestMapping("queryAllByLimit")
    public Map<String ,Object> queryAllByLimit(Integer rows, Integer page) {
        return videoService.queryAllByLimit(rows,page);
    }

    /**
     * 将文件上传到阿里云
     * */

    @RequestMapping("fileUpLoadAliYun")
    public Integer fileUpLoadAliYun(MultipartFile path, String id) {
        videoService.fileUpLoadAliYun(path,id);
        return 12;
    }
    /**
     * 视频的编辑
     * */
    @RequestMapping("edit")
    public String edit(Video video, String oper, String[] id) {
        return videoService.edit(video, oper, id);
    }

    /**
     *Es检索视频数据信息
     *  */
    @RequestMapping("querySearch")
    public List<Video> querySearch(String content) {
        List<Video> videos = videoService.querySearch(content);
        return videos;
    }
    /**
     * 高亮检索视频的数据信息
     * @param content 视频的title brief
     * */
    @RequestMapping("higHlightSearch")
    public List<Video> higHlightSearch(String content) {
        System.out.println("content = " + content);
        List<Video> videos = videoService.higHlightSearch(content);
        for (Video video : videos) {
            System.out.println("video = " + video);
        }
        return videos;
    }
}

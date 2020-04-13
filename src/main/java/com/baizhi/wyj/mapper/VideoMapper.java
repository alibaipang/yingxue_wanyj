package com.baizhi.wyj.mapper;

import com.baizhi.wyj.entity.Video;
import com.baizhi.wyj.entity.VideoExample;
import java.util.List;

import com.baizhi.wyj.vo.VideoVo;
import com.baizhi.wyj.vo.VideoVo2;
import com.baizhi.wyj.vo.VideoVo3;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface VideoMapper extends Mapper<Video> {
    /**后天的数据展示*
     */
    List<Video> queryAllByLimit(Integer page,Integer rows);
    /**前台的数据展示
     * */
    List<VideoVo> queryByReleaseTime();
    /**视频详情
     * */
    VideoVo3 queryByVideoDetail(String videoId,String userId);
    /**
     * 该类别下的视频信息
     * */
    List<VideoVo2> queryByVideoDetail2(String cateId,String notCateId);
    /**
     * 模糊查询视频的信息
     * */
    List<VideoVo2> queryByLikeVideoName(String content);
    /**
     * 添加视频
     * */
    void save(Video video);
}
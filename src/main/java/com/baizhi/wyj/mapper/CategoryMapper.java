package com.baizhi.wyj.mapper;

import com.baizhi.wyj.entity.Category;
import com.baizhi.wyj.vo.CategoryVo;
import com.baizhi.wyj.vo.VideoVo2;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {
    List<CategoryVo> queryAllCategory();
    //查询一级类别

    List<CategoryVo> queryAllCategory1();
    //查询二级类别

    List<CategoryVo> queryAllCategory2(String pid);
    //查询二级类别下的视频

    List<VideoVo2>queryCateVideoList(String cateId);
}
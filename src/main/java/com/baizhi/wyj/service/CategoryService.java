package com.baizhi.wyj.service;


import com.baizhi.wyj.entity.Category;
import com.baizhi.wyj.vo.CategoryVo;
import com.baizhi.wyj.vo.VideoVo2;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    /**
     * 后台的方法
     * */
    //查询所有的类别

    HashMap<String,Object> queryAllByLimit(Integer rows, Integer page,String pId);
    //编辑类别

    HashMap<String,String> edit(Category category, String oper, String pId, String[] id);
    /**
     *前台接口的方法
     *  */
    //查询所有的一级类别包括底下的二级类别

    List<CategoryVo> queryAllCategory();

    //查询二级类别下的视频

    List<VideoVo2>queryCateVideoList(String cateId);
}

package com.baizhi.wyj.controller;

import com.baizhi.wyj.entity.Category;
import com.baizhi.wyj.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("category")
@RestController
public class CategoryController {
    @Resource
    CategoryService categoryService;
    /**
     * 分页查询
     * rows 是一页展示的数据条数
     * page 是当前页数
     * PId 是父id*/
    @RequestMapping("queryCategoryByLimit")
    public Map<String, Object> queryCategoryByLimit(Integer rows, Integer page,String pId){
        Map<String, Object> map = categoryService.queryAllByLimit(rows, page,pId);
        return map;
    }
    /**
     * 类别的编辑
     * oper: add、edit、del、
     * PId父类的id
     * id 批量删除的id数组*/
    @RequestMapping("edit")
    public HashMap<String,String> edit(Category category, String oper, String pId, String[] id){
        HashMap<String, String> hashMap = categoryService.edit(category, oper, pId, id);
        return hashMap;
    }
}

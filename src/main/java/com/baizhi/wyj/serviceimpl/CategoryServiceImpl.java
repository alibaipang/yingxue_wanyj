package com.baizhi.wyj.serviceimpl;

import com.baizhi.wyj.annotation.AddCache;
import com.baizhi.wyj.annotation.DelCahe;
import com.baizhi.wyj.annotation.LogAnnotation;
import com.baizhi.wyj.entity.Category;
import com.baizhi.wyj.entity.CategoryExample;
import com.baizhi.wyj.entity.Video;
import com.baizhi.wyj.entity.VideoExample;
import com.baizhi.wyj.mapper.CategoryMapper;
import com.baizhi.wyj.mapper.VideoMapper;
import com.baizhi.wyj.service.CategoryService;
import com.baizhi.wyj.util.UUIDUtil;
import com.baizhi.wyj.vo.CategoryVo;
import com.baizhi.wyj.vo.VideoVo2;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    VideoMapper videoMapper;

    /**
     * 分页查询类别
     * records 总条数
     * total（总条数除以每页展示的条数  是否有余数）   总页数
     * page  当前页
     * rows   数据
     */
    @Override
    @AddCache
    public HashMap<String,Object> queryAllByLimit(Integer rows, Integer page, String pId) {
        //分页查询、忽略几条数据、获取几条数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        CategoryExample example = new CategoryExample();
        //判断是级别查询还是二级查询
        if (pId == null) {
            example.createCriteria().andLevelsEqualTo("1");
        } else {
            example.createCriteria().andParentIdEqualTo(pId);
            example.createCriteria().andLevelsEqualTo("2");
        }
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        //查询总条数、计算总页数
        Integer records = categoryMapper.selectCountByExample(example);
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        //将jqgrid需要的四个参数put进map里面、一json形式返回给页面
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("records", records);
        hashMap.put("total", total);
        hashMap.put("page", page);
        hashMap.put("rows", categories);
        return hashMap;
    }

    /**
     * 类别的编辑
     */
    @LogAnnotation(name = "类别的编辑操作")
    @Override
    @DelCahe
    public HashMap<String,String> edit(Category category, String oper, String pId, String[] id) {
        //页面的提示信息的存储、以json的形式返回给页面
        HashMap<String,String > map = new HashMap<>();
        //添加操作
        if ("add".equals(oper)) {
            //判断是一级添加还是二级添加
            if (pId == null) {
                String uuid = UUIDUtil.getUUID();
                category.setId(uuid);
                category.setLevels("1");
                category.setParentId(null);
                categoryMapper.insertSelective(category);
            } else {
                String uuid = UUIDUtil.getUUID();
                category.setId(uuid);
                category.setLevels("2");
                category.setParentId(pId);
                categoryMapper.insertSelective(category);
            }
            //修改操作
        } else if ("edit".equals(oper)) {
            CategoryExample example = new CategoryExample();
            example.createCriteria().andIdEqualTo(category.getId());
            categoryMapper.updateByExampleSelective(category, example);
            //删除操作
        }else if ("del".equals(oper)){
            for (String s : id){
                //判断是一级还是二级删除
                if (pId == null) {
                    CategoryExample example = new CategoryExample();
                    example.createCriteria().andParentIdEqualTo(s);
                    List<Category> list = categoryMapper.selectByExample(example);
                    //判断一级类别下有没有二级类别、如果有就不能删除
                    if (list.size()==0) {
                        categoryMapper.deleteByPrimaryKey(s);
                        map.put("status","200");
                        map.put("message","删除成功");
                    }else {
                        map.put("status","400");
                        map.put("message","删除失败、该类别下有子类别");
                    }
                } else {
                    //判断二级类别下是否有视频、如果有的话就不能删除
                    VideoExample videoExample = new VideoExample();
                    videoExample.createCriteria().andCategoryIdEqualTo(s);
                    List<Video> videos = videoMapper.selectByExample(videoExample);
                    if (videos.size()==0){
                        categoryMapper.deleteByPrimaryKey(s);
                        map.put("message","删除成功");
                        map.put("status","200");
                    }else {
                        map.put("message","删除失败、该类别下有有视频");
                        map.put("status","400");
                    }
                }
            }

        }
        return map;
    }

    /**
     * 前台的接口
     * 首页展示的方法
     * */

    /**
     * 查询所有一级类别包括底下的二级类别*/
    @Override
    @AddCache
    public List<CategoryVo> queryAllCategory() {
        List<CategoryVo> list1 = categoryMapper.queryAllCategory1();
        for (CategoryVo categoryVo : list1) {
            String id = categoryVo.getId();
            List<CategoryVo> list2 = categoryMapper.queryAllCategory2(id);
            categoryVo.setCategoryList(list2);
        }
        return list1;
    }
    /**
     * 查询二级类别下的视频*/
    @Override
    @AddCache
    public List<VideoVo2> queryCateVideoList(String cateId) {
        List<VideoVo2> videoVo2s = categoryMapper.queryCateVideoList(cateId);
        //设置视频的点赞数、根据视频的id查询该视频下的点赞数
        for (VideoVo2 videoVo2 : videoVo2s) {
            videoVo2.setLikeCount(12);
        }
        return videoVo2s;
    }
}

package com.baizhi.wyj.serviceimpl;

import com.baizhi.wyj.annotation.AddCache;
import com.baizhi.wyj.annotation.DelCahe;
import com.baizhi.wyj.annotation.LogAnnotation;
import com.baizhi.wyj.entity.*;
import com.baizhi.wyj.mapper.CategoryMapper;
import com.baizhi.wyj.mapper.UserMapper;
import com.baizhi.wyj.mapper.VideoMapper;
import com.baizhi.wyj.repository.VideoRepository;
import com.baizhi.wyj.service.VideoService;
import com.baizhi.wyj.util.AliyunOssUtil;
import com.baizhi.wyj.util.UUIDUtil;
import com.baizhi.wyj.vo.VideoVo;
import com.baizhi.wyj.vo.VideoVo2;
import com.baizhi.wyj.vo.VideoVo3;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

@Service
public class VideoServiceImpl implements VideoService {
    @Resource
    VideoMapper videoMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    VideoRepository videoRepository;

    /**
     *后台的管理方法
     * */



    /**
     *视频的分页查询
     * @param rows 是每页展示的条数
     * @param page 是当前页
     * */


    @Override
    @AddCache
    public Map<String, Object> queryAllByLimit(Integer rows, Integer page) {
        HashMap<String, Object> map = new HashMap<>();
        //分页查询、忽略几条、获取几条
        List<Video> videos = videoMapper.queryAllByLimit((page - 1) * rows, rows);
        //查询总条数、计算总页数
        Integer records = videoMapper.selectCount(new Video());
        Integer total = records%rows==0?records/rows:records/rows+1;
        //将total records rows page 存放在map里面、以json的形式传给页面
        map.put("page",page);
        map.put("rows",videos);
        map.put("total",total);
        map.put("records",records);
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            System.out.println("stringObjectEntry = " + stringObjectEntry);
        }
        return map;
    }


    /**
     * 视频上传到阿里云
     * 获取视频的第一帧、并将封面上传到阿里云
     * @param path  文件上传的属性
     * @param id  根据视频的id
     * */


    @Override
    @DelCahe
    public void fileUpLoadAliYun(MultipartFile path, String id) {
        //获取文件名、给文件名拼接上一个时间戳
        String filename = path.getOriginalFilename();
        String newName = System.currentTimeMillis()+"-"+filename;
        //拼接视频的目录
        String newNames = "videos/"+newName;
        //视频封面的name格式设置
        String[] split = newName.split("\\.");
        String intercept = split[0];
        String coverName =intercept+".jpg";
        //将文件上传到阿里云
        AliyunOssUtil.uploadFileBytes(path,newNames);
        //修改视频的属性
        Video video = videoMapper.selectByPrimaryKey(id);
        //获取视频的第一帧、并将封面上传到阿里云
        AliyunOssUtil.intercptVideo(newNames,"videos/videocovers/"+coverName);
        //修改视频的path以及cover
        video.setPath("https://yingx-ali.oss-cn-beijing.aliyuncs.com/videos/"+newName);
        video.setCover("https://yingx-ali.oss-cn-beijing.aliyuncs.com/videos/videocovers/"+coverName);
        videoMapper.updateByPrimaryKeySelective(video);
        //在elasticsearch的索引库中添加、更新的视频信息
        videoRepository.save(video);
    }

    /**
     * 编辑视频
     * @param video 视频
     * @param  oper 编辑的类型
     * @param  id 删除的id集合
     * */

    @LogAnnotation(name = "视频的编辑操作")
    @Override
    @DelCahe
    public String edit(Video video, String oper, String[] id) {
        String uuid = null;
        if ("add".equals(oper)){
            uuid = UUID.randomUUID().toString();
            video.setId(uuid);
            video.setPublishDate(new Date());
            video.setCategoryId("e0ba022f522046a79082393553f8cd05");
            video.setGroupId("12");
            video.setUserId("355987bec47c4a619d17082683ac8c51");
            videoMapper.insertSelective(video);
        }else if ("edit".equals(oper)){
            VideoExample videoExample = new VideoExample();
            uuid = video.getId();
            videoExample.createCriteria().andIdEqualTo(uuid);
            //在修改区之前、删除阿里云的视频以及视频的封面
            List<Video> videos = videoMapper.selectByExample(videoExample);
            for (Video video1 : videos) {
                //对于视频的path 和cover 进行拆分拼接、进行删除视频和视频的封面
                String[] pathName = video1.getPath().split("/");
                String[] coverName = video1.getCover().split("/");
                String imagePath = coverName[3]+"/"+coverName[4]+"/"+coverName[5];
                String videoPath = pathName[3]+"/"+pathName[4];
                AliyunOssUtil.deltealiyun(videoPath);
                AliyunOssUtil.deltealiyun(imagePath);
            }
            //执行修改操作
            videoMapper.updateByExampleSelective(video,videoExample);
        }else if ("del".equals(oper)){
            for (String ids : id) {
                System.out.println("ids = " + ids);
                //删除库里面的数据
                videoMapper.deleteByPrimaryKey(ids);
                //根据id查询视频
                VideoExample videoExample = new VideoExample();
                videoExample.createCriteria().andIdEqualTo(ids);
                List<Video> videos = videoMapper.selectByExample(videoExample);
                for (Video video1 : videos) {
                    //对于视频的path 和cover 进行拆分拼接、进行删除视频和视频的封面
                    String[] pathName = video1.getPath().split("/");
                    String[] coverName = video1.getCover().split("/");
                    String videoPath = pathName[3]+"/"+pathName[4];
                    String imagePath = coverName[3]+"/"+coverName[4]+"/"+coverName[5];
                    AliyunOssUtil.deltealiyun(videoPath);
                    AliyunOssUtil.deltealiyun(imagePath);
                }
                //删除elasticsearch索引库中你要删除的数据
                videoRepository.deleteById(ids);
            }
        }
        return uuid;
    }

    /**
     * 前端接口的方法
     * */



    /**
     * 首页展示的方法
     * */

    @Override
    @AddCache
    public List<VideoVo> queryByReleaseTime() {
        List<VideoVo> VideoVos = videoMapper.queryByReleaseTime();
        //获取视频的点赞数、先使用固定的值、后期进行处理
        Integer likeCount = 12;
        for (VideoVo videoVo : VideoVos) {
            videoVo.setLikeCount(likeCount);
        }

        return VideoVos;
    }

    /**
     * 视频的详情
     * */
    @Override
    @AddCache
    public VideoVo3 queryByVideoDetail(String videoId, String cateId ,String userId) {
        System.out.println("videoId = " + videoId);
        System.out.println("cateId = " + cateId);
        System.out.println("userId = " + userId);
        VideoVo3 videoVo3 = videoMapper.queryByVideoDetail(videoId, userId);
        if (videoVo3!=null){
            //查询该类别下的所有视频
            List<VideoVo2> videoVo2s = videoMapper.queryByVideoDetail2(cateId,videoVo3.getCategoryId());
            for (VideoVo2 videoVo2 : videoVo2s) {
                videoVo2.setLikeCount(12);
            }
            videoVo3.setVideoList(videoVo2s);
        }
        System.out.println("videoVo3 --- " + videoVo3);
        return videoVo3;
    }

    /**
     * 模糊查询
     * */


    @Override
    @AddCache
    public List<VideoVo2> queryByLikeVideoName(String content) {
        List<VideoVo2> videoVo2s = videoMapper.queryByLikeVideoName(content);
        return videoVo2s;
    }

    /**
     * 添加视频
     * */


    @Override
    @DelCahe
    public VideoVo2 save( ) {
        //设置视频的库数据
        Video video = new Video();
        video.setId(UUIDUtil.getUUID());
        video.setUserId("355987bec47c4a619d17082683ac8c51");
        video.setPath("https://yingx-ali.oss-cn-beijing.aliyuncs.com/videos/1585915912426-烂屁眼.mp4");
        video.setCover("https://yingx-ali.oss-cn-beijing.aliyuncs.com/videos/videocovers/1585915912426-烂屁眼.jpg");
        video.setGroupId("12");
        video.setCategoryId("e0ba022f522046a79082393553f8cd05");
        video.setPublishDate(new Date());
        video.setBrief("这时个视频");
        video.setTitle("就是视频");
        videoMapper.save(video);
        //设置视频的前端返回数据
        VideoVo2 videoVo2 = new VideoVo2();
        videoVo2.setId(video.getId());
        //获取用户的名字
        User user = userMapper.selectByPrimaryKey(video.getUserId());
        videoVo2.setUserName(user.getUsername());
        videoVo2.setVideoTitle(video.getTitle());
        videoVo2.setPath(video.getPath());
        videoVo2.setCover(video.getCover());
        videoVo2.setDescription(video.getBrief());
        videoVo2.setCategoryId(video.getCategoryId());
        //获取类别的名称
        Category category = categoryMapper.selectByPrimaryKey(video.getCategoryId());
        videoVo2.setCateName(category.getCateName());
        videoVo2.setUploadTime(video.getPublishDate());
        //设置视频的点赞数
        videoVo2.setLikeCount(0);
        return videoVo2;
    }
    /**
     * 检索视频
     * @param content 视频的title brief */
    @Override
    public List<Video> querySearch(String content) {
        //指定查询的规则
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))
                .build();
        //执行查询
        List<Video> videos = elasticsearchTemplate.queryForList(build, Video.class);
        return videos;
    }
    /**
     * 高亮检索视频的数据信息
     * @param content 视频的title brief
     * */
    @Override
    public List<Video> higHlightSearch(String content) {
        ArrayList<Video> list = new ArrayList<>();
        //处理高亮
        HighlightBuilder.Field field = new HighlightBuilder
                .Field("*")
                .preTags("<span style = 'color:red'>")
                .postTags("</span>");
        //指定查询的规则
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))
                .withHighlightFields(field)
                .build();
        //高亮的查询
        AggregatedPage<Video> videos = elasticsearchTemplate.queryForPage(build, Video.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                //获取查询的结果
                SearchHit[] hits = searchResponse.getHits().getHits();
                for (SearchHit hit : hits) {
                    //获取元数据
                    Map<String, Object> map = hit.getSourceAsMap();
                    //获取数据字段并进行非空判断
                    String id = map.get("id")!=null?map.get("id").toString() : null;
                    String title = map.get("title")!=null? map.get("title").toString() : null;
                    String brief = map.get("brief")!=null? map.get("brief").toString() : null;
                    String path = map.get("path")!=null? map.get("path").toString() : null;
                    String cover = map.get("cover")!=null? map.get("cover").toString() : null;
                    String categoryId = map.get("categoryId")!=null?map.get("categoryId").toString() : null;
                    String groupId = map.get("groupId")!=null?map.get("groupId").toString() : null;
                    String userId = map.get("userId")!=null?map.get("userId").toString() : null;
                    Date publishDate = map.get("publishDate")!=null?new Date(Long.valueOf(map.get("publishDate").toString())) : null;
                    //设置返回的数据
                    Video video = new Video(id,title,brief,path,cover,publishDate,categoryId,groupId,userId,null);
                    //处理高亮的数据、进行非空的判断并重新对返回的数据赋值
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    if (title!=null){
                        if (highlightFields.get("title")!=null){
                            video.setTitle(highlightFields.get("title").fragments()[0].toString());
                        }
                    }
                    if (title!=null){
                        if (highlightFields.get("brief")!=null){
                            System.out.println("=========="+highlightFields.get("brief").fragments()[0].toString());
                            video.setBrief(highlightFields.get("brief").fragments()[0].toString());
                        }
                    }
                    list.add(video);
                }
                return new AggregatedPageImpl<T>((List<T>)list);
            }
        });
        return videos.getContent();
    }

}

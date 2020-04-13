package testvideo;

import com.baizhi.wyj.application;
import com.baizhi.wyj.entity.Video;
import com.baizhi.wyj.entity.VideoExample;
import com.baizhi.wyj.mapper.VideoMapper;
import com.baizhi.wyj.repository.VideoRepository;
import com.baizhi.wyj.service.VideoService;
import com.baizhi.wyj.util.AliyunShortMessage;
import com.baizhi.wyj.vo.VideoVo2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = application.class)
@RunWith(value = SpringRunner.class)
public class videotest {
    @Resource
    VideoService videoService;
    @Resource
    VideoMapper videoMapper;
    @Resource
    ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    VideoRepository videoRepository;
    @Test
    public void find() {
        List<Video> videos = videoMapper.queryAllByLimit(0, 3);
        for (Video video : videos) {
            System.out.println("video = " + video);
        }
    }
    @Test
    public void find2() {
        Map<String, Object> map = videoService.queryAllByLimit(3, 1);
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            System.out.println("stringObjectEntry = " + stringObjectEntry);
        }
    }

    @Test
    public void save() {
        List<Video> videos = videoMapper.selectAll();
        for (Video video : videos) {
            System.out.println("video = " + video);
            Video video1 = new Video();
            video1.setId(video.getId());
            video1.setTitle(video.getTitle());
            video1.setBrief(video.getBrief());
            video1.setPath(video.getPath());
            video1.setCover(video.getCover());
            video1.setPublishDate(video.getPublishDate());
            video1.setUserId(video.getUserId());
            video1.setGroupId(video.getGroupId());
            video1.setCategoryId(video.getCategoryId());
            video1.setCategory(video.getCategory());
            Video save = videoRepository.save(video1);
            System.out.println("save = " + save);

        }
    }

    @Test
    public void ali() {
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andIdEqualTo("3f76e371-bb47-47cd-a507-18724f147415");
        int i = videoMapper.deleteByExample(videoExample);

        System.out.println("i = " + i);
    }

    @Test
    public void al453i() {

                //videoRepository.deleteById("fU7-bXEBA604Zbws8jDW");
        videoRepository.deleteAll();
    }
}
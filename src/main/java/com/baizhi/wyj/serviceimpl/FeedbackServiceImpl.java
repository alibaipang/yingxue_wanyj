package com.baizhi.wyj.serviceimpl;

import com.baizhi.wyj.entity.Feedback;
import com.baizhi.wyj.entity.FeedbackExample;
import com.baizhi.wyj.mapper.FeedbackMapper;
import com.baizhi.wyj.service.FeedbackService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    FeedbackMapper feedbackMapper;

    /**
     * 分页查询用户的数据
     * records 总条数
     * total（总条数除以每页展示的条数  是否有余数）   总页数
     * page  当前页
     * rows   数据*/

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAllByLimit(Integer rows, Integer page) {
        HashMap<String, Object> map = new HashMap<>();
        //分页查询、忽略几条、获取几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Feedback> feedbacks = feedbackMapper.selectByExampleAndRowBounds(new FeedbackExample(), rowBounds);
        //查询总条数、计算总页数
        Integer records = feedbackMapper.selectCount(new Feedback());
        Integer total = records%rows==0?records/rows:records/rows+1;
        map.put("records",records);
        map.put("rows",feedbacks);
        map.put("total",total);
        map.put("page",page);
        return map;
    }
}

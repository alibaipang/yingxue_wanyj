package com.baizhi.wyj.serviceimpl;

import com.baizhi.wyj.annotation.AddCache;
import com.baizhi.wyj.entity.Log;
import com.baizhi.wyj.entity.LogExample;
import com.baizhi.wyj.mapper.LogMapper;
import com.baizhi.wyj.service.LogService;
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
public class LogServiceImpl implements LogService {
    @Resource
    LogMapper logMapper;

    /**
     * 分页查询用户的数据
     * records 总条数
     * total（总条数除以每页展示的条数  是否有余数）   总页数
     * page  当前页
     * rows   数据*/

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAllByLimit(Integer rows, Integer page) {
        //分页查询、忽略几条、获取几条
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Log> logs = logMapper.selectByExampleAndRowBounds(new LogExample(), rowBounds);
        //查询总条数、计算总页数
        Integer records = logMapper.selectCount(new Log());
        Integer total = records%rows==0?records/rows:records/rows+1;
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",logs);
        map.put("page",page);
        map.put("records",records);
        return map;
    }
}

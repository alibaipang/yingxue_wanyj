package com.baizhi.wyj.controller;

import com.baizhi.wyj.service.FeedbackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping("feedback")
@RestController
public class FeedbackController {
    @Resource
    FeedbackService feedbackService;

    /**
     * 分页查询所有数据
     * rows:每页展示的数据条数
     * page：当前页
     */

    @RequestMapping("findAllByLimit")
    public Map<String ,Object> findAllByLimit(Integer rows, Integer page) {
        return feedbackService.queryAllByLimit(rows,page);
    }
}

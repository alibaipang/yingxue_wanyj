package com.baizhi.wyj.service;



import java.util.Map;

public interface FeedbackService {
    Map<String ,Object> queryAllByLimit(Integer rows, Integer page);
}

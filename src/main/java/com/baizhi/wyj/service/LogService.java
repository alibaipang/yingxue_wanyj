package com.baizhi.wyj.service;



import java.util.Map;

public interface LogService {
    Map<String ,Object> queryAllByLimit(Integer rows, Integer page);
}

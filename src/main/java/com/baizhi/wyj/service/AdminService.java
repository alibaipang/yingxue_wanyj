package com.baizhi.wyj.service;

import com.baizhi.wyj.entity.Admin;

import java.util.HashMap;

public interface AdminService {
    HashMap<String,String> login(Admin admin, String code);
}

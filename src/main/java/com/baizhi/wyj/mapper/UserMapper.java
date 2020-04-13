package com.baizhi.wyj.mapper;

import com.baizhi.wyj.entity.User;
import com.baizhi.wyj.entity.UserExample;
import java.util.List;

import com.baizhi.wyj.vo.UserAddress;
import com.baizhi.wyj.vo.UserVo2;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    List<UserVo2> queryUserByMonth(String sex);
    List<UserAddress>queryUserByAddress(String sex);
}
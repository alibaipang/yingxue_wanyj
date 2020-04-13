package user;

import com.baizhi.wyj.application;
import com.baizhi.wyj.mapper.UserMapper;
import com.baizhi.wyj.vo.UserAddress;
import com.baizhi.wyj.vo.UserVo2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest(classes = application.class)
@RunWith(value = SpringRunner.class)
public class UserTest {
    @Resource
    UserMapper userMapper;

    @Test
    public void name() {
        List<UserAddress> f = userMapper.queryUserByAddress("M");
        for (UserAddress userAddress : f) {
            System.out.println("userAddress = " + userAddress);
        }
    }

    @Test
    public void git() {
        System.out.println("测试Git与idea的集成");
    }
}






package testAdmin.Admin;

import com.baizhi.wyj.application;
import com.baizhi.wyj.entity.AdminExample;
import com.baizhi.wyj.entity.User;
import com.baizhi.wyj.mapper.AdminMapper;
import com.baizhi.wyj.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = application.class)
@RunWith(value = SpringRunner.class)
public class Admin {
    @Resource
    AdminMapper adminMapper;
    @Resource
    UserMapper userMapper;

    @Test
    public void find() {
        List list = adminMapper.selectAll();
        list.forEach(admin-> System.out.println(admin));
        //相当于是一个条件，没有条件对所有数据进行分页
        AdminExample example = new AdminExample();
        RowBounds rowBounds = new RowBounds(0,5);
        List<User> users = userMapper.selectByExampleAndRowBounds(example, rowBounds);
        for (User user : users) {
            System.out.println("user = " + user);
        }
    }
}


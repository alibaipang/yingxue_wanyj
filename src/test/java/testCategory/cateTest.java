
package testCategory;

import com.baizhi.wyj.application;
import com.baizhi.wyj.entity.AdminExample;
import com.baizhi.wyj.entity.User;
import com.baizhi.wyj.mapper.AdminMapper;
import com.baizhi.wyj.mapper.CategoryMapper;
import com.baizhi.wyj.mapper.UserMapper;
import com.baizhi.wyj.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = application.class)
@RunWith(value = SpringRunner.class)
public class cateTest {
    @Resource
    AdminMapper adminMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    CategoryService categoryService;

    @Test
    public void find() {
        Map<String, Object> map = categoryService.queryAllByLimit(5, 1,"1");
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            System.out.println("stringObjectEntry = " + stringObjectEntry);
        }
    }
}

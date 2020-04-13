package goeasy;

import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class goeasyTest {
    @Test
    public void name() {
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-7f40d4e429584d36a6c6806352b81183");
        goEasy.publish("my_channel", "Hello, GoEasy!");
    }

    @Test
    public void testGoEasyUser(){

        //添加用户

        for (int i = 0; i < 100; i++) {

            Random random = new Random();
            //获取随机数  参数i：50  0<=i<50
            //int i = random.nextInt(50);

            HashMap<String, Object> map = new HashMap<>();

            //根据月份 性别 查询数量   //查询用户信息
            map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月"));
            map.put("man", Arrays.asList(random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(200)));
            map.put("woman", Arrays.asList(random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(500),random.nextInt(500)));

            //将对象转为json格式字符串
            String content = JSON.toJSONString(map);

            //配置发送消息的必要配置  参数：regionHost,服务器地址,自己的appKey
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-7f40d4e429584d36a6c6806352b81183");

            //配置发送消息  参数:管道名称（自定义）,发送内容
            goEasy.publish("yingx_ali", content);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

}

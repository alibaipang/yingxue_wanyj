package com.baizhi.wyj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.wyj.mapper")
@org.mybatis.spring.annotation.MapperScan("com.baizhi.wyj.mapper")
public class application {
    public static void main(String[] args) {
        SpringApplication.run(application.class,args);
    }
}

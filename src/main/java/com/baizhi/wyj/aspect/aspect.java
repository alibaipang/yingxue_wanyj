package com.baizhi.wyj.aspect;

import com.baizhi.wyj.annotation.LogAnnotation;
import com.baizhi.wyj.entity.Admin;
import com.baizhi.wyj.entity.Log;
import com.baizhi.wyj.mapper.LogMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

//表示该类为切面类
@Aspect
//表示将该类交给工厂管理
@Component
@Transactional
public class aspect {
    @Resource
    LogMapper logMapper;
    @Resource
    HttpSession session;
    /**
     * 构建切入点
     * */
    @Pointcut(value = "@annotation(com.baizhi.wyj.annotation.LogAnnotation)")
    public void pt(){}
    /**5b7eda38e19b4a598b502a0b117f5c65
     * 构建切面
     * */
    @Around(value = "pt()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取user的对象
        Admin admin = (Admin) session.getAttribute("admin");
        //设置日志的属性
        String result = "error";
        //获取目标方法对象
        MethodSignature methodSignature=(MethodSignature)proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //根据方法对象获取方法上的注解
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        //根据注解获取注解的属性
        String name = annotation.name();
        Object object = null;
        try {
             object = proceedingJoinPoint.proceed();
            result = "success";
        } catch (Throwable throwable) {
            throw throwable;
        }finally {
            //设置日志的信息
            Log log = new Log();
            log.setId(UUID.randomUUID().toString());
            log.setName("root");
            log.setDate(new Date());
            log.setResult(result);
            log.setWhat(name);
            logMapper.insertSelective(log);
        }
        return object;
    }
}

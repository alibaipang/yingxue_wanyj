package com.baizhi.wyj.aspect;

import com.baizhi.wyj.annotation.AddCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Set;

@Component
@Aspect
public class RedisCache {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    RedisTemplate redisTemplate;

    /**为redis添加缓存、
     * key==类的权限定名+方法名+实参名、
     * value==数据
     * */
    //@Around("execution(* com.baizhi.wyj.serviceimpl.*.query*(..))")
    public Object addCache(ProceedingJoinPoint point){
        System.out.println("进入缓存");
        //解决序列化乱码
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        //类的权限定名
        String className = point.getArgs().getClass().getName();
        //方法名
        String methodName = point.getSignature().getName();
        // 实参名
        Object[] argName = point.getArgs();
        //拼接redis的key
        StringBuilder builder = new StringBuilder();
        builder.append(className).append(methodName);
        for (Object o : argName) {
            builder.append(o);
        }
        String key = builder.toString();
        //在redis的缓存中判断key是否存在、存在的话直接取出结果、不存在的话拿到结果在加入缓存
        Object CacheResult = null;
        if (redisTemplate.hasKey(key)){
            CacheResult = redisTemplate.opsForValue().get(key);
        }else{
            try {
                CacheResult = point.proceed();
            } catch (Throwable throwable) {

            }
            redisTemplate.opsForValue().set(key,CacheResult);
        }
        return CacheResult;
    }
    /**
     * 添加缓存
     * */
    //@Around("@annotation(com.baizhi.wyj.annotation.AddCache)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        System.out.println("==环绕通知==");

        /*解决缓存乱码*/
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        //获取切面切的方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        //判断该方法上是否有添加缓存的注解
        boolean annotationPresent = method.isAnnotationPresent(AddCache.class);

        if(annotationPresent){
            StringBuilder sb = new StringBuilder();
            //设计一个key(类名+方法名+实参)   value(查询的结果)
            //获取类的全限定名
            String clazzName = proceedingJoinPoint.getTarget().getClass().getName();
            sb.append(clazzName);
            System.out.println("clazzName = " + clazzName);
            //获取方法名
            String methodName = proceedingJoinPoint.getSignature().getName();
            sb.append(methodName);
            System.out.println("methodName = " + methodName);
            //返回方法的实参
            Object[] args = proceedingJoinPoint.getArgs();
            for (Object arg : args) {
                sb.append(arg);
                System.out.println("arg = " + arg);
            }

            //获取key
            String key = sb.toString();
            //String 类型的操作
            ValueOperations valueOperations = redisTemplate.opsForValue();

            //判断key是否存在
            Boolean aBoolean = redisTemplate.hasKey(key);

            Object result = null;
            //判断缓存中对否存在
            if(aBoolean){
                //存在 查询redis 返回结果
                result = valueOperations.get(key);
            }else{
                result = proceedingJoinPoint.proceed();
                //不存在  纳入redis缓存
                valueOperations.set(key,result);
            }
            System.out.println("result = " + result);
            return result;
        }else{
            //没有该注解直接放行
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        }
    }
    /*
     * 清空缓存
     * */
    //@After("@annotation(com.baizhi.wyj.annotation.DelCahe)")
    public void after(JoinPoint joinPoint){

        System.out.println("==清空缓存==");
        //类的全限定名
        String className = joinPoint.getTarget().getClass().getName();

        //获取所有的key
        Set<String> keys = stringRedisTemplate.keys("*");
        //遍历所有的key
        for (String key : keys) {

            //判断符合条件的key
            if(key.startsWith(className)){
                //清除
                stringRedisTemplate.delete(key);
            }
        }
    }
    //@Around("@annotation(com.baizhi.wyj.annotation.AddCache)")
    public Object addCahe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("环绕通知");

        //序列化解决乱码
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        StringBuilder sb = new StringBuilder();
        //获取类的全限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        sb.append(methodName);

        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }
        //拼接key   小key
        String key = sb.toString();
        //取出key
        Boolean aBoolean = redisTemplate.opsForHash().hasKey(className, key);
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object result =null;
        //去Redis判断key是否存在
        if(aBoolean){
            //存在缓存中有数据取出数据返回结果
            result = hashOperations.get(className,key);
        }else{
            //不存在缓存中没有放行方法得到结果
            result = proceedingJoinPoint.proceed();
            //拿到返回结果加入缓存
            hashOperations.put(className,key,result);
        }
        return result;
    }

    //@After("@annotation(com.baizhi.wyj.annotation.DelCahe)")
    public void delCache(JoinPoint joinPoint){
        //清空缓存获取类的全限定名
        String className = joinPoint.getTarget().getClass().getName();
        //删除该类下所有的数据
        redisTemplate.delete(className);
    }
}

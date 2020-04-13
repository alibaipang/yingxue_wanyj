package com.baizhi.wyj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 阿狸
 * ElementType.METHOD 指定当前注解的范围
 * RetentionPolicy.RUNTIME 指定当前注解在运行时生效
 * */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    String name();
}

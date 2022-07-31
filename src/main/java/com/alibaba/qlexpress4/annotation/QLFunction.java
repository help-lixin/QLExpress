package com.alibaba.qlexpress4.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @Author TaoKan
 * @Date 2022/7/31 上午10:44
 */
@Inherited
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QLFunction {
    /**
     * 注解内容
     */
    String[] value();
}

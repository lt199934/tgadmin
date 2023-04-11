package com.tg.admin.config;

import java.lang.annotation.*;

/**
 * @Program: admin
 * @ClassName Auth
 * @Author: liutao
 * @Description:
 * @Create: 2023-03-20 08:11
 * @Version 1.0
 **/

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
     String value() default " ";
}

package com.fan.apibackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 19677
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    /**
     * 必须具有角色
     * @return
     */
    String mustRole() default "";

    /**
     * 任意角色
     * @return
     */
    String[] anyRole() default "";
}

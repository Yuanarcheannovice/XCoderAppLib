package com.xcoder.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类名：Injection 类描述：对象注入
 * ----------------------------------------------------------
 * 
 * 
 * ----------------------------------------------------------
 */

@Target(value = { ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Injection {

}

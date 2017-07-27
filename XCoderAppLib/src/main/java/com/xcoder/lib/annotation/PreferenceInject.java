package com.xcoder.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类名：PreferenceInject 类描述：文件存储对象注入
 * ----------------------------------------------------------
 * 
 * 
 * ----------------------------------------------------------
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreferenceInject {
	String value();
}

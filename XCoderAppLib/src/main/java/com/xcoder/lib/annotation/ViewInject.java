package com.xcoder.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类名：ViewInject 类描述：view对象注入
 * ----------------------------------------------------------
 * 
 * 
 * ----------------------------------------------------------
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {

	int value();

	/* parent view id */
	int parentId() default 0;
}

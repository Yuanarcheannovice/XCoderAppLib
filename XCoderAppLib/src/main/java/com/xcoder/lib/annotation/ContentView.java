package com.xcoder.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类名：ContentView 类描述：控制层对象注入
 * ----------------------------------------------------------
 * 
 * 
 * ----------------------------------------------------------
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {
	int value();
}

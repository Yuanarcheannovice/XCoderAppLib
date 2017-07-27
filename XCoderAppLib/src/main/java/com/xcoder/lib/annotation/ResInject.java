package com.xcoder.lib.annotation;



import com.xcoder.lib.injection.ResType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类名：ResInject 类描述：资源对象注入
 * ----------------------------------------------------------
 * 
 * 
 * ----------------------------------------------------------
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResInject {
	int id();

	ResType type();
}

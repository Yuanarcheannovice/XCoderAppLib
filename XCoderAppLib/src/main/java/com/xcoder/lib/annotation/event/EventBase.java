package com.xcoder.lib.annotation.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* 类名：EventBase
* 作者：
* 创建时间：2014-10-24-上午10:50:35
* 修改记录：
*/

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
   Class<?> listenerType();

   String listenerSetter();

   String methodName();
}

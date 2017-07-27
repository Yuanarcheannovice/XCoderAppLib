 

package com.xcoder.lib.annotation.event;

import android.widget.AdapterView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* 类名：OnItemLongClick
* 类描述：长按监听事件
*/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
       listenerType = AdapterView.OnItemLongClickListener.class,
       listenerSetter = "setOnItemLongClickListener",
       methodName = "onItemLongClick")
public @interface OnItemLongClick {
   int[] value();

   int[] parentId() default 0;
}

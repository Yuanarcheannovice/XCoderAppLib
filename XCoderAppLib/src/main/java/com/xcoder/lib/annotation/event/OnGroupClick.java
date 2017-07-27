
package com.xcoder.lib.annotation.event;

import android.widget.ExpandableListView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* 类名：OnGroupClick
* 作者：CJ

*/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
       listenerType = ExpandableListView.OnGroupClickListener.class,
       listenerSetter = "setOnGroupClickListener",
       methodName = "onGroupClick")
public @interface OnGroupClick {
   int[] value();

   int[] parentId() default 0;
}

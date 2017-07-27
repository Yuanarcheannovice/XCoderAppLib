 
package com.xcoder.lib.annotation.event;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* 类名：OnKey

*/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(
       listenerType = View.OnKeyListener.class,
       listenerSetter = "setOnKeyListener",
       methodName = "onKey")
public @interface OnKey {
   int[] value();

   int[] parentId() default 0;
}

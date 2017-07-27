package com.xcoder.lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类名：Model 类描述：模型 修改记录：
 * ----------------------------------------------------------
 * 
 * 
 * ----------------------------------------------------------
 */

@Target(value = { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

	/**
	 * 方法说明：类别名 方法名称：name
	 * 
	 * @return 返回值：String
	 */
	public int id();
}

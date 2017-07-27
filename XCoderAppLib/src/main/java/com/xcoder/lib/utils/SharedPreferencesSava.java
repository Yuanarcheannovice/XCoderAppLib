package com.xcoder.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xcoder.lib.app.BaseApplication;

import java.io.IOException;
import java.io.StreamCorruptedException;

public class SharedPreferencesSava {
	private static SharedPreferencesSava instance = null;

	public static SharedPreferencesSava getInstance() {
		if (instance == null) {
			instance = new SharedPreferencesSava();
		}
		return instance;
	}
	


	/**
	 * @方法说明:保存对象
	 * @方法名称:savaObject
	 * @param context
	 * @param spName
	 * @param key
	 * @param ob
	 * @返回void
	 */
	public boolean savaObject(Context context, String spName, String key,
			Object ob) {
		if (ob == null) {
			return false;
		}
		boolean falg = false;
		SharedPreferences preferences = BaseApplication.context
				.getSharedPreferences(spName, Context.MODE_PRIVATE);
		String str = SerializableUtil.objToStr(ob);
		falg = preferences.edit().putString(key, str).commit();
		return falg;
	}

	/**
	 * @方法说明:获取存储的对象
	 * @方法名称:getObject
	 * @param context
	 * @param spName
	 * @param key
	 * @return
	 * @返回值:Object
	 */
	public Object getObject(Context context, String spName, String key) {
		Object ob = null;
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		String productBase64 = preferences.getString(key, "");
		try {
			ob = SerializableUtil.strToObj(productBase64);
		} catch (StreamCorruptedException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return ob;
	}

	public void removeAct(Context context, String spName, String key) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		preferences.edit().remove(key);
	}

	/**
	 * @方法说明:存储int数据
	 * @方法名称:savaIntValue
	 * @param context
	 * @param spName
	 * @param key
	 * @param value
	 * @返回void
	 */
	public void savaIntValue(Context context, String spName, String key,
			int value) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		preferences.edit().putInt(key, value).commit();
	}

	/**
	 * @方法说明:获取int数据
	 * @方法名称:getIntValue
	 * @param context
	 * @param spName
	 * @param key
	 * @return
	 * @返回int
	 */
	public int getIntValue(Context context, String spName, String key) {
		return getIntValue(context, spName, key, 0);
	}

	/**
	 * @方法说明:获取int数据
	 * @方法名称:getIntValue
	 * @param context
	 * @param spName
	 * @param key
	 * @param defaultValue
	 * @return
	 * @返回int
	 */
	public int getIntValue(Context context, String spName, String key,
			int defaultValue) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		return preferences.getInt(key, defaultValue);
	}

	/**
	 * @方法说明:存储float数据
	 * @方法名称:savaFloatValue
	 * @param context
	 * @param spName
	 * @param key
	 * @param value
	 * @返回void
	 */
	public void savaFloatValue(Context context, String spName, String key,
			float value) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		preferences.edit().putFloat(key, value).commit();
	}

	/**
	 * @方法说明:获取float数据
	 * @方法名称:getFloatValue
	 * @param context
	 * @param spName
	 * @param key
	 * @return
	 * @返回float
	 */
	public float getFloatValue(Context context, String spName, String key) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		return preferences.getFloat(key, 0);
	}

	/**
	 * @方法说明:存储boolean数据
	 * @方法名称:savaBooleanValue
	 * @param context
	 * @param spName
	 * @param key
	 * @param value
	 * @返回void
	 */
	public void savaBooleanValue(Context context, String spName, String key,
			boolean value) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		preferences.edit().putBoolean(key, value).commit();
	}

	/**
	 * @方法说明:获取boolean数据
	 * @方法名称:getBooleanValue
	 * @param context
	 * @param spName
	 * @param key
	 * @return
	 * @返回boolean
	 */
	public boolean getBooleanValue(Context context, String spName, String key) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	/**
	 * @方法说明:获取boolean数据
	 * @方法名称:getBooleanValue
	 * @param context
	 * @param spName
	 * @param key
	 * @param isDefault
	 * @return
	 * @返回boolean
	 */
	public boolean getBooleanValue(Context context, String spName, String key,
			boolean isDefault) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		return preferences.getBoolean(key, isDefault);
	}

	/**
	 * @方法说明:存储long数据
	 * @方法名称:savaLongValue
	 * @param context
	 * @param spName
	 * @param key
	 * @param value
	 * @返回void
	 */
	public void savaLongValue(Context context, String spName, String key,
			long value) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		preferences.edit().putLong(key, value).commit();
	}

	/**
	 * @方法说明:获取long数据
	 * @方法名称:getLongValue
	 * @param context
	 * @param spName
	 * @param key
	 * @return
	 * @返回long
	 */
	public long getLongValue(Context context, String spName, String key) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		return preferences.getLong(key, 0);
	}

	/**
	 * @方法说明:存储String数据
	 * @方法名称:savaStringValue
	 * @param context
	 * @param spName
	 * @param key
	 * @param value
	 * @返回void
	 */
	public void savaStringValue(Context context, String spName, String key,
			String value) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		preferences.edit().putString(key, value).commit();
	}

	/**
	 * @方法说明:获取String数据
	 * @方法名称:getStringValue
	 * @param context
	 * @param spName
	 * @param key
	 * @return
	 * @返回String
	 */
	public String getStringValue(Context context, String spName, String key) {
		SharedPreferences preferences = context.getSharedPreferences(spName,
				Context.MODE_PRIVATE);
		return preferences.getString(key, "");
	}
}

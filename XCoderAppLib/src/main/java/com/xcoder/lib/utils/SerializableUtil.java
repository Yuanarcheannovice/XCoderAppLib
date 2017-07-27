package com.xcoder.lib.utils;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @类名:SerializableUtil
 * @类描述:objToStr,listToStr,strToObj,strToList转换类
 * @作者:Administrator
 * @修改时间:
 * @修改备注:
 * @版本:
 */
public class SerializableUtil {

	/**
	 * @方法说明:将集合转换成String类型
	 * @方法名称:listToString
	 * @param list
	 * @return
	 * @throws IOException
	 * @返回值:String
	 */
	public static <E> String listToString(List<E> list) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		String listString = "";
		try {
			// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件
			baos = new ByteArrayOutputStream();
			// 然后将得到的字符数据封装到ObjectOutputStream(baos)
			oos = new ObjectOutputStream(baos);
			// writeObject 方法负责写入特定类的对象的状态，以便相应的readObject可以还原他
			oos.writeObject(list);
			// 最后，用Base64.encode将字节文件转换成Base64编码，并以String形式保存
			listString = Base64.encodeToString(baos.toByteArray(),
					Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭oos
				if (oos != null) {
					oos.flush();
					oos.close();
					oos = null;
				}
				// 关闭baos
				if (baos != null) {
					baos.flush();
					baos.close();
					baos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listString.trim();
	}

	/**
	 * @方法说明:将数组转换成String类型
	 * @方法名称:listToString
	 * @param list
	 * @return
	 * @throws IOException
	 * @返回值:String
	 */
	public static <E> String mapToString(
			Map<? extends Object, ? extends Object> map) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		String listString = "";
		try {
			// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件
			baos = new ByteArrayOutputStream();
			// 然后将得到的字符数据封装到ObjectOutputStream(baos)
			oos = new ObjectOutputStream(baos);
			// writeObject 方法负责写入特定类的对象的状态，以便相应的readObject可以还原他
			oos.writeObject(map);
			// 最后，用Base64.encode将字节文件转换成Base64编码，并以String形式保存
			listString = Base64.encodeToString(baos.toByteArray(),
					Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭oos
				if (oos != null) {
					oos.flush();
					oos.close();
					oos = null;
				}
				// 关闭baos
				if (baos != null) {
					baos.flush();
					baos.close();
					baos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listString.trim();
	}

	/**
	 * @方法说明:将对象转换成String类型
	 * @方法名称:objToStr
	 * @param obj
	 * @return
	 * @throws IOException
	 * @返回值:String
	 */
	public static String objToStr(Object obj) {
		if (obj == null) {
			return "";
		}

		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		String listString = "";
		try {
			// 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件
			baos = new ByteArrayOutputStream();
			// 然后将得到的字符数据装载到ObjectOutputStream
			oos = new ObjectOutputStream(baos);
			// writeObject 方法负责写入特定类的对象的状态，以便相应的readObject可以还原它
			oos.writeObject(obj);
			// 最后，用Base64.encode将字节文件转换成Base64编码，并以String形式保存
			listString = Base64.encodeToString(baos.toByteArray(),
					Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭oos
				if (oos != null) {
					oos.flush();
					oos.close();
					oos = null;
				}
				// 关闭baos
				if (baos != null) {
					baos.flush();
					baos.close();
					baos = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listString.trim();
	}

	/**
	 * @方法说明:将String转换成Obj
	 * @方法名称:strToObj
	 * @param str
	 * @return
	 * @throws StreamCorruptedException
	 * @throws IOException
	 * @返回值:Object
	 */
	public static Object strToObj(String str) throws
			IOException {
		byte[] mByte = Base64.decode(str, Base64.DEFAULT);
		ByteArrayInputStream bais = new ByteArrayInputStream(mByte);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
				ois = null;
			}

			if (bais != null) {
				bais.close();
				bais = null;
			}
		}
		return null;
	}

	/**
	 * @方法说明:将String转换成List<E>
	 * @方法名称:strToList
	 * @param str
	 * @return
	 * @throws StreamCorruptedException
	 * @throws IOException
	 * @返回值:List<E>
	 */
	@SuppressWarnings("unchecked")
	public static <E> ArrayList<E> strToList(String str)
			throws IOException {
		byte[] mByte = Base64.decode(str, Base64.DEFAULT);
		ByteArrayInputStream bais = new ByteArrayInputStream(mByte);
		ObjectInputStream ois = null;
		ArrayList<E> stringList = null;
		try {
			ois = new ObjectInputStream(bais);
			stringList = (ArrayList<E>) ois.readObject();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
				ois = null;
			}

			if (bais != null) {
				bais.close();
				bais = null;
			}
		}
		return stringList;
	}

	/**
	 * @方法说明:将String转换成List<E>
	 * @方法名称:strToList
	 * @param str
	 * @return
	 * @throws StreamCorruptedException
	 * @throws IOException
	 * @返回值:List<E>
	 */
	@SuppressWarnings("unchecked")
	public static <E> Map<? extends Object, ? extends Object> strToMap(
			String str) throws IOException {
		byte[] mByte = Base64.decode(str, Base64.DEFAULT);
		ByteArrayInputStream bais = new ByteArrayInputStream(mByte);
		ObjectInputStream ois = null;
		Map<? extends Object, ? extends Object> stringList = null;
		try {
			ois = new ObjectInputStream(bais);
			stringList = (Map<? extends Object, ? extends Object>) ois
					.readObject();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
				ois = null;
			}

			if (bais != null) {
				bais.close();
				bais = null;
			}
		}
		return stringList;
	}
}

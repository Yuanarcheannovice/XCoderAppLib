package com.xcoder.lib.injection;

import android.app.Activity;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.PreferenceInject;
import com.xcoder.lib.annotation.ResInject;
import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.lib.annotation.event.EventBase;
import com.xcoder.lib.utils.LogUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;



/**
 * @类名:ViewUtils
 * @类描述:对象注入
 * @作者:Administrator
 */
public class ViewUtils {

	private ViewUtils() {
	}

	public static void inject(View view) {
		injectObject(view, new ViewFinder(view));
	}

	public static void inject(Activity activity) {
		injectObject(activity, new ViewFinder(activity));
	}

	public static void inject(PreferenceActivity preferenceActivity) {
		injectObject(preferenceActivity, new ViewFinder(preferenceActivity));
	}

	public static void inject(Object handler, View view) {
		injectObject(handler, new ViewFinder(view));
	}

	public static void inject(Object handler, Activity activity) {
		injectObject(handler, new ViewFinder(activity));
	}

	public static void inject(Object handler, PreferenceGroup preferenceGroup) {
		injectObject(handler, new ViewFinder(preferenceGroup));
	}

	public static void inject(Object handler,
			PreferenceActivity preferenceActivity) {
		injectObject(handler, new ViewFinder(preferenceActivity));
	}

	/**
	 * 方法说明：对象注入绑定 方法名称：injectObject
	 * 
	 * @param handler
	 * @param finder
	 *            返回值：void
	 */
	private static void injectObject(Object handler, ViewFinder finder) {

		Class<?> handlerType = handler.getClass();

		// inject ContentView
		ContentView contentView = handlerType.getAnnotation(ContentView.class);
		if (contentView != null) {
			try {
				Method setContentViewMethod = handlerType.getMethod(
						"setContentView", int.class);
				setContentViewMethod.invoke(handler, contentView.value());
			} catch (Throwable e) {
				LogUtils.e(e.getMessage(), e);
			}
		}

		// inject view
		Field[] fields = handlerType.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if (viewInject != null) {
					try {
						View view = finder.findViewById(viewInject.value(),
								viewInject.parentId());
						if (view != null) {
							field.setAccessible(true);
							field.set(handler, view);
						}
					} catch (Throwable e) {
						LogUtils.e(e.getMessage(), e);
					}
				} else {
					ResInject resInject = field.getAnnotation(ResInject.class);
					if (resInject != null) {
						try {
							Object res = ResLoader.loadRes(resInject.type(),
									finder.getContext(), resInject.id());
							if (res != null) {
								field.setAccessible(true);
								field.set(handler, res);
							}
						} catch (Throwable e) {
							LogUtils.e(e.getMessage(), e);
						}
					} else {
						PreferenceInject preferenceInject = field
								.getAnnotation(PreferenceInject.class);
						if (preferenceInject != null) {
							try {
								Preference preference = finder
										.findPreference(preferenceInject
												.value());
								if (preference != null) {
									field.setAccessible(true);
									field.set(handler, preference);
								}
							} catch (Throwable e) {
								LogUtils.e(e.getMessage(), e);
							}
						} else {
							Injection injection = field
									.getAnnotation(Injection.class);
							if (injection != null) {
								try {
									field.setAccessible(true);
									Object obj = Class.forName(
											field.getType().getCanonicalName())
											.newInstance();
									field.set(handler, obj);
									injectionField(obj, finder);
								} catch (Throwable e) {
									LogUtils.e(e.getMessage(), e);
								}
							}
						}
					}
				}
			}
		}

		// inject event
		Method[] methods = handlerType.getDeclaredMethods();
		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				Annotation[] annotations = method.getDeclaredAnnotations();
				if (annotations != null && annotations.length > 0) {
					for (Annotation annotation : annotations) {
						Class<?> annType = annotation.annotationType();
						if (annType.getAnnotation(EventBase.class) != null) {
							method.setAccessible(true);
							try {
								Method valueMethod = annType
										.getDeclaredMethod("value");
								Method parentIdMethod = null;
								try {
									parentIdMethod = annType
											.getDeclaredMethod("parentId");
								} catch (Throwable e) {
								}
								Object values = valueMethod.invoke(annotation);
								Object parentIds = parentIdMethod == null ? null
										: parentIdMethod.invoke(annotation);
								int parentIdsLen = parentIds == null ? 0
										: Array.getLength(parentIds);
								int len = Array.getLength(values);
								for (int i = 0; i < len; i++) {
									ViewInjectInfo info = new ViewInjectInfo();
									info.value = Array.get(values, i);
									info.parentId = parentIdsLen > i ? (Integer) Array
											.get(parentIds, i) : 0;
									EventListenerManager.addEventMethod(finder,
											info, annotation, handler, method);
								}
							} catch (Throwable e) {
								LogUtils.e(e.getMessage(), e);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 方法说明：对象注入绑定递归函数 方法名称：injectionField
	 * 
	 * @param handler
	 * @param finder
	 *            返回值：void
	 */
	private static void injectionField(Object handler, ViewFinder finder) {
		Class<?> handlerType = handler.getClass();
		// inject view
		Field[] fields = handlerType.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if (viewInject != null) {
					try {
						View view = finder.findViewById(viewInject.value(),
								viewInject.parentId());
						if (view != null) {
							field.setAccessible(true);
							field.set(handler, view);
						}
					} catch (Throwable e) {
						LogUtils.e(e.getMessage(), e);
					}
				} else {
					ResInject resInject = field.getAnnotation(ResInject.class);
					if (resInject != null) {
						try {
							Object res = ResLoader.loadRes(resInject.type(),
									finder.getContext(), resInject.id());
							if (res != null) {
								field.setAccessible(true);
								field.set(handler, res);
							}
						} catch (Throwable e) {
							LogUtils.e(e.getMessage(), e);
						}
					} else {
						PreferenceInject preferenceInject = field
								.getAnnotation(PreferenceInject.class);
						if (preferenceInject != null) {
							try {
								Preference preference = finder
										.findPreference(preferenceInject
												.value());
								if (preference != null) {
									field.setAccessible(true);
									field.set(handler, preference);
								}
							} catch (Throwable e) {
								LogUtils.e(e.getMessage(), e);
							}
						} else {
							Injection injection = field
									.getAnnotation(Injection.class);
							if (injection != null) {
								try {
									field.setAccessible(true);
									Object obj = Class.forName(
											field.getType().getCanonicalName())
											.newInstance();
									field.set(handler, obj);
									injectionField(obj, finder);
								} catch (Throwable e) {
									LogUtils.e(e.getMessage(), e);
								}
							}
						}
					}
				}
			}
		}

		// inject event
		Method[] methods = handlerType.getDeclaredMethods();
		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				Annotation[] annotations = method.getDeclaredAnnotations();
				if (annotations != null && annotations.length > 0) {
					for (Annotation annotation : annotations) {
						Class<?> annType = annotation.annotationType();
						if (annType.getAnnotation(EventBase.class) != null) {
							method.setAccessible(true);
							try {
								Method valueMethod = annType
										.getDeclaredMethod("value");
								Method parentIdMethod = null;
								try {
									parentIdMethod = annType
											.getDeclaredMethod("parentId");
								} catch (Throwable e) {
								}
								Object values = valueMethod.invoke(annotation);
								Object parentIds = parentIdMethod == null ? null
										: parentIdMethod.invoke(annotation);
								int parentIdsLen = parentIds == null ? 0
										: Array.getLength(parentIds);
								int len = Array.getLength(values);
								for (int i = 0; i < len; i++) {
									ViewInjectInfo info = new ViewInjectInfo();
									info.value = Array.get(values, i);
									info.parentId = parentIdsLen > i ? (Integer) Array
											.get(parentIds, i) : 0;
									EventListenerManager.addEventMethod(finder,
											info, annotation, handler, method);
								}
							} catch (Throwable e) {
								LogUtils.e(e.getMessage(), e);
							}
						}
					}
				}
			}
		}
	}
}

package com.wh.whtth.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class CastClassUtils {
	
	@SuppressWarnings("unchecked")
	public static Object getObjectFromParam(Map<String, Object> params,
			Class cls) {
		Object o = null;
		Field[] fields = cls.getDeclaredFields();

		try {
			o = cls.newInstance();
			for (Field f : fields) {
				Method setMed = cls.getDeclaredMethod(
						"set" + captureName(f.getName()),f.getType());
				Object param = params.get(f.getName());
				if (param != null) {
					if (f.getType() == Integer.TYPE
							|| f.getType() == Integer.class) {
						setMed.invoke(o, Integer.parseInt(param+""));
					} else if (f.getType() == Long.TYPE
							|| f.getType() == Long.class) {
						setMed.invoke(o, Long.parseLong(param+""));
					} else if (f.getType() == Double.TYPE
							|| f.getType() == Double.class) {
						setMed.invoke(o, Double.parseDouble(param+""));
					} else if (f.getType() == Boolean.TYPE
							|| f.getType() == Boolean.class) {
						setMed.invoke(o, Boolean.parseBoolean(param+""));
					} else {
						setMed.invoke(o, f.getType().cast(param));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return o;
	}
	
	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}
	
	public static void main(String[] args) {
//		Map<String,String> 
	}
}

package com.wh.whtth.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfym = new SimpleDateFormat("yyyy-MM");
	
	public static String formatYmdhms(){
		return sdf.format(new Date());
	}
	
	public static String formatYmd(){
		return sdfymd.format(new Date());
	}
	
	public static String formatYm(){
		return sdfym.format(new Date());
	}
	
	public static void main(String[] args) {
		System.out.println(formatYmd());
	}
}

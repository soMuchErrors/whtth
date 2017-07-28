package com.wh.whtth.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wh.whtth.util.DateUtil;

public class Test2 {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(DateUtil.formatYmd().replace("-", ""));
	}
}

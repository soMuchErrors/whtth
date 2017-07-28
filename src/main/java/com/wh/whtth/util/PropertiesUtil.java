package com.wh.whtth.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ruibang.jedis.util.Constants;

public class PropertiesUtil {
	
	public static Properties  loadProperties(String filename){
		Properties pro = null;
		InputStream in = null;
		try {
			pro = new Properties();
			in=PropertiesUtil.class.getResourceAsStream(filename);
			pro.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					
				}
			}
		}
		return pro;
	}
}

package com.wh.whtth.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class ReqJsonUtil {
	public static String getJsonString(HttpServletRequest request) {
		BufferedReader br;
		String s;
		try {
			br = request.getReader();
			StringBuffer sb = new StringBuffer();
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

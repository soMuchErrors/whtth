package com.wh.whtth.util;

import java.util.HashMap;
import java.util.Map;

public class ConstantsUtil {
	/**
	 * baseContext
	 *   --domain
	 *       --operationVo
	 */
	public static Map<String,HashMap<String,String>> baseParams = new HashMap<String,HashMap<String,String>>();
	public final static String voDomain = "com.wh.whtth.vo.";
	public final static String voSubfix = "Vo";
	public final static String serviceDomain = "com.wh.whtth.service.impl.";
	public final static String serviceSubfix = "Service";
	
	public final static String verifySubfix = "verify:";
	
	static{
		baseParams.put("shopManage", getHashMap("shopManage"));
	}
	
	
	public static HashMap<String,String> getHashMap(String domain){
		HashMap<String,String> h = new HashMap<String,String>();
		h.put("service", domain+serviceSubfix);
		StringBuffer sb = new StringBuffer(domain);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		String ss = sb.toString();
		h.put("viewVo", voDomain+"ViewVo");
		h.put("eidtVo", voDomain+ss+voSubfix);
		return h;
	}
}

package com.wh.whtth.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.wh.whtth.util.ReqJsonUtil;
import com.wh.whtth.vo.ShopManageVo;

public class BaseController {

	public static Object getVo(HttpServletRequest req,Class voclass) throws Exception {
		Object vo = null;
		String s = "";
		s = ReqJsonUtil.getJsonString(req);
		JSONObject jsonObj = JSONObject.parseObject(s);
		vo = JSONObject.toJavaObject(jsonObj, voclass);
		return vo;
	}
	
	public static void wrint(HttpServletResponse res,Object o) throws IOException{
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json;charset=utf-8");
		Writer pt = res.getWriter();
		pt.write(JSONObject.toJSONString(o));
		pt.flush();
		pt.close();
	}
}

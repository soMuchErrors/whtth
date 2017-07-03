package com.wh.whtth.controller;

import com.alibaba.fastjson.JSONObject;
import com.wh.whtth.util.ReqJsonUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class BaseController {

	@Value("#{'imgpath'}")
	protected String IMG_PATH;

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

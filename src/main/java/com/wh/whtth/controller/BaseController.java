package com.wh.whtth.controller;

import com.alibaba.fastjson.JSONObject;
import com.wh.whtth.util.ReqJsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

public class BaseController {

	protected String IMG_PATH = "/whtthimg/";

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

	/**
	 * Gets the root path of server.
	 *
	 * @return the root path
	 */
	public static String getRootPath() {
		String classPath = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		String rootPath = "";

		/** For Windows */
		if ("\\".equals(File.separator)) {
			String path = classPath.substring(1,
					classPath.indexOf("/WEB-INF/classes"));
			rootPath = path.substring(0, path.lastIndexOf("/"));
			rootPath = rootPath.replace("/", "\\");
		}

		/** For Linux */
		if ("/".equals(File.separator)) {
			String path = classPath.substring(0,
					classPath.indexOf("/WEB-INF/classes"));
			rootPath = path.substring(0, path.lastIndexOf("/"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}
}

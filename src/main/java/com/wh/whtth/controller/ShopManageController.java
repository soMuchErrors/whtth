package com.wh.whtth.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wh.whtth.util.ConstantsUtil;
import com.wh.whtth.util.ReqJsonUtil;
import com.wh.whtth.vo.ShopManageVo;
import com.wh.whtth.vo.ViewVo;

@Controller
@RequestMapping("/base")
public class ShopManageController {
	
	@RequestMapping(value="/{domain}/{operation}",method = RequestMethod.POST,headers = {})
	public void view(@PathVariable(value="domain")String domain,
			@PathVariable(value="operation")String operation,
			HttpServletRequest req,HttpServletResponse res) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class voClass = Class.forName(ConstantsUtil.baseParams.get(domain).get(operation+"Vo"));
		Object service = ContextLoader.getCurrentWebApplicationContext().getBean(ConstantsUtil.baseParams.get(domain).get("service"));
		Method method = service.getClass().getMethod(operation,Object.class);
		Object vo = null;
		String s = "";
		try {
			s = ReqJsonUtil.getJsonString(req);
			JSONObject jsonObj  = JSONObject.parseObject(s);
			 
			vo =  JSONObject.toJavaObject(jsonObj, voClass);
		} catch (Exception e) {
			res.setStatus(HttpServletResponse.SC_NO_CONTENT);
			return;
		}
		
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json;charset=utf-8");
		Writer wr = res.getWriter();
		wr.write(JSONObject.toJSONString(method.invoke(service, vo)));
		wr.flush();
		wr.close();
	}
	
}

package com.wh.whtth.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ruibang.jedis.util.MultipleJedisPool;
import com.wh.whtth.controller.BaseController;
import com.wh.whtth.vo.Message;

import redis.clients.jedis.Jedis;

public class BaseInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		Jedis jedis = MultipleJedisPool.getJedisFromPool();
		try {
			String token = request.getParameter("token");
			if(token==null){
				map.put("message", new Message("2","非法操作！"));
				BaseController.wrint(response, map);
				return false;
			}
				
			String userid = jedis.hget(token,"userid");
			if(userid==null){
				map.put("message", new Message("2","登陆超时，重新登陆！"));
				BaseController.wrint(response, map);
				return false;
			}
			jedis.expire(token, 5*60);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			MultipleJedisPool.jedisRelease(jedis);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}

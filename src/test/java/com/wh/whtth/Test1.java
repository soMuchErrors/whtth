package com.wh.whtth;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wh.whtth.controller.ShopManageController;
import com.wh.whtth.idao.UserMapper;
import com.wh.whtth.model.User;
import com.wh.whtth.service.AdminService;
import com.wh.whtth.service.ShopManageService;
import com.wh.whtth.vo.ShopManageVo;
import com.wh.whtth.vo.ViewVo;

public class Test1 {
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
		UserMapper service = (UserMapper) context.getBean("userDao");
		User u = service.selectByIdcard("5421");
		System.out.println(u.getNickname());
	}
}

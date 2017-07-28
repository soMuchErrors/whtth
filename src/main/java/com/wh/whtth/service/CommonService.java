package com.wh.whtth.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wh.whtth.idao.UserMapper;
import com.wh.whtth.model.User;
import com.wh.whtth.util.EncryptUtil;
import com.wh.whtth.vo.Message;

@Service("commonService")
public class CommonService {
	
	@Resource(name = "userDao")
	private UserMapper userDao;
	
	public Map<String, Object> login(String username, String password) {
		return userDao.login(username, EncryptUtil.PBEEncrypt(password));
	}

	public Map<String, Object> register(String username, String password) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(userDao.selectByPhonenum(username)!=null){
			map.put("message", new Message("0","该手机号已被注册"));
			return map;
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(EncryptUtil.PBEEncrypt(password));
		user.setState("1");
		user.setUserType("2");
		user.setBalance(Double.parseDouble("0"));
		if(userDao.insertSelective(user)==0){
			map.put("message", new Message("0","注册失败"));
			return map;
		}
		map.put("user", userDao.login(username, EncryptUtil.PBEEncrypt(password)));
		map.put("message", new Message("1","注册成功"));
		return map;
	}

	public Object getShopId(String string) {
		return null;
	}

}

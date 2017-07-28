package com.wh.whtth.service;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.wh.whtth.idao.*;
import com.wh.whtth.model.Product;
import com.wh.whtth.model.Shop;
import com.wh.whtth.model.User;
import com.wh.whtth.util.EncryptUtil;
import com.wh.whtth.util.QRCodeUtil;
import com.wh.whtth.vo.Message;
import com.wh.whtth.vo.ShopManageVo;
import com.wh.whtth.vo.ViewVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("adminService")
public class AdminService {
	@Value("${imgpath}")
	private String imgurl;
	
	@Value("${imgsubfix}")
	private String imgsubfix;
	
	@Resource(name = "shopDao")
	private ShopMapper shopDao;
	
	@Resource(name = "userDao")
	private UserMapper userDao;
	
	@Resource(name = "productDao")
	private ProductMapper productDao;
	
	@Resource(name = "orderDao")
	private OrderMapper orderDao;

	@Resource(name = "baseCodeDao")
	private BaseCodeMapper baseCodeDao;
	
	@Transactional
	public Map<String,Object> addShopManager(ShopManageVo vo) throws WriterException, IOException{
		Map<String,Object> rtn = new HashMap<String,Object>();
		int shopstate =0;
		int userstate = 0;
		if(userDao.selectByPhonenum(vo.getUser().getPhonenum())!=null){
			rtn.put("message", new Message("0","该手机号已被注册"));
			return rtn;
		}
			
		if(userDao.selectByIdcard(vo.getUser().getIdcardNum())!=null){
			rtn.put("message", new Message("0","该身份证已被注册"));
			return rtn;
		}
		User user = vo.getUser();
		Shop shop = vo.getShop();
		user.setState("1");
		user.setUserType("1");
		user.setNickname(user.getName());
		user.setUsername(vo.getUser().getPhonenum());
		user.setPassword(EncryptUtil.PBEEncrypt("123456"));
		user.setBalance(Double.parseDouble("0"));
		userstate = userDao.insert(user);
		//TODO 对商铺的收款链接预留
		Map<String,String>  QCcontext = new HashMap<String,String>();
		User user1 = userDao.selectByPhonenum(vo.getUser().getPhonenum());
		QCcontext.put("shoper", user1.getId()+"");
		QCcontext.put("linkurl", "/user/payto");
		
		String filename = File.separator
				+"qccode"
				+ File.separator + UUID.randomUUID()+".png";
		
		QRCodeUtil.QREncode(imgurl, filename, JSONObject.toJSONString(QCcontext));
		shop.setPaymentcode(imgsubfix+filename);
		
		shop.setUserid(user1.getId());
		shop.setTelphone(vo.getUser().getPhonenum());
		shop.setState(1);
		shopstate = shopDao.insert(shop);
		if(shopstate==0 && userstate==0 )
			rtn.put("message", new Message("0","添加失败"));
		else
			rtn.put("message", new Message("1","添加成功"));
		return rtn;
	}

	
	//有效商铺列表
	public Map<String,Object> listValidShops(ViewVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		vo.setEnd(vo.getPagesize()*vo.getPage());
		List<Map<String,String>> list = shopDao.listValidShops(vo);
		map.put("list", list);
		map.put("page", vo.getPage());
		map.put("total", shopDao.countValidShops());
		map.put("message", new Message("1"));
		return map;
	}
	
	//禁止商铺列表
	public Object listInvalidShops(ViewVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		vo.setEnd(vo.getPagesize()*vo.getPage());
		List<Map<String,String>> list =shopDao.listInvalidShops(vo);
		map.put("list", list);
		map.put("page", vo.getPage());
		map.put("total", shopDao.countInvalidShops());
		map.put("message", new Message("1"));
		return map;
	}
	
	public Object makeShopInvalid(String id) {
		Shop shop = new Shop();
		shop.setId(Long.parseLong(id));
		shop.setState(0);
		return edit(shop);
	}
	
	public Object makeShopValid(String id) {
		Shop shop = new Shop();
		shop.setId(Long.parseLong(id));
		shop.setState(1);
		return edit(shop);
	}
	
	public Object setRate(String id, String discount, String pushmoney) {
		Shop shop = new Shop();
		shop.setId(Long.parseLong(id));
		if(!StringUtils.isEmpty(discount))
			shop.setDiscount(Double.parseDouble(discount));
		if(!StringUtils.isEmpty(pushmoney))
			shop.setPushmoney(Double.parseDouble(pushmoney));
		return edit(shop);
	}
	
	
	public Object edit(Shop shop){
		Map<String,Object> rtn = new HashMap<String,Object>();
		int state = shopDao.updateByPrimaryKeySelective(shop);
		if(state > 0)
			rtn.put("message", new Message("1","更新成功"));
		else
			rtn.put("message", new Message("0","更新失败"));
		return rtn;
	}
	
	//查询待审核商品列表
	public Object checkPendingProducts(ViewVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		vo.setEnd(vo.getPagesize()*vo.getPage());
		List<Map<String,String>> list =productDao.checkPendingProducts(vo);
		map.put("list", list);
		map.put("page", vo.getPage());
		map.put("total", productDao.countPendingProducts());
		map.put("message", new Message("1"));
		
		return map;
	}

	public Object approveAudit(String id) {
		Product pro = new Product();
		pro.setId(Long.parseLong(id));
		pro.setAuditstate("2");
		return edit(pro);
	}
	
	public Object edit(Product pro){
		Map<String,Object> rtn = new HashMap<String,Object>();
		int state = productDao.updateByPrimaryKeySelective(pro);
		if(state > 0)
			rtn.put("message", new Message("1","更新成功"));
		else
			rtn.put("message", new Message("1","更新失败"));
		return rtn;
	}

	public Object nonApproval(String id) {
		Product pro = new Product();
		pro.setId(Long.parseLong(id));
		pro.setAuditstate("3");
		return edit(pro);
	}

	public Object viewProduct(String id) {
		return productDao.selectByPrimaryKey(Long.parseLong(id));
	}
	
	public Object addUser(User vo) {
		Map<String,Object> rtn = new HashMap<String,Object>();
		if(userDao.selectByPhonenum(vo.getPhonenum())!=null){
			rtn.put("message", new Message("0","该手机号已被注册"));
			return rtn;
		}
			
		if(userDao.selectByIdcard(vo.getIdcardNum())!=null){
			rtn.put("message", new Message("0","该身份证已被注册"));
			return rtn;
		}
		vo.setState("1");
		vo.setUserType("2");
		vo.setUsername(vo.getPhonenum());
		vo.setPassword(EncryptUtil.PBEEncrypt("123456"));
		vo.setBalance(Double.parseDouble("0"));
		int userstate = userDao.insert(vo);
		if(userstate==0 )
			rtn.put("message", new Message("0","添加失败"));
		else
			rtn.put("message", new Message("1","添加成功"));
		return rtn;
	}

	public Object makeUserInvalid(String id) {
		User user = new User();
		user.setId(Long.parseLong(id));
		user.setState("0");
		return edit(user);
	}
	
	public Object edit(User user){
		Map<String,Object> rtn = new HashMap<String,Object>();
		int state = userDao.updateByPrimaryKeySelective(user);
		if(state > 0)
			rtn.put("message", new Message("1","更新成功"));
		else
			rtn.put("message", new Message("0","更新失败"));
		return rtn;
	}

	public Object makeUserValid(String id) {
		User user = new User();
		user.setId(Long.parseLong(id));
		user.setState("1");
		return edit(user);
	}
	
	public Object recharge(String id, String money) {
		Map<String,Object> rtn = new HashMap<String,Object>();
		User user = userDao.selectByPrimaryKey(Long.parseLong(id));
		double balance = user.getBalance()+Integer.parseInt(money);
		user.setBalance(balance);
		int state = userDao.updateByPrimaryKeySelective(user);
		if(state > 0)
			rtn.put("message", new Message("1","充值成功"));
		else
			rtn.put("message", new Message("0","充值失败"));
		return rtn;
	}

	public Object userinfo(String id) {
		return userDao.selectByPrimaryKey(Long.parseLong(id));
	}
	
	//个人消费列表
	public Object shoppingInfo(ViewVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		vo.setEnd(vo.getPagesize()*vo.getPage());
		List<Map<String,String>> list = orderDao.shoppingInfo(vo);
		map.put("list", list);
		map.put("page", vo.getPage());
		map.put("total", orderDao.countShoppingInfo(vo.getId()));
		map.put("message", new Message("1"));
		return map;
	}


	public Object getTradeList() {
		return baseCodeDao.showTrade();
	}

	//有效会员列表
	public Object listValidUser(ViewVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		vo.setEnd(vo.getPagesize()*vo.getPage());
		List<Map<String,String>> list = userDao.listValidUser(vo);
		map.put("list", list);
		map.put("page", vo.getPage());
		map.put("total", userDao.countValidUser());
		map.put("message", new Message("1"));
		return map;
	}


	public Object listInvalidUser(ViewVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		vo.setEnd(vo.getPagesize()*vo.getPage());
		List<Map<String,String>> list = userDao.listInvalidUser(vo);
		map.put("list", list);
		map.put("page", vo.getPage());
		map.put("total", userDao.countInvalidUser());
		map.put("message", new Message("1"));
		return map;
	}
	
	
}

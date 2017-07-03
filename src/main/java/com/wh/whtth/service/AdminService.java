package com.wh.whtth.service;

import com.wh.whtth.idao.*;
import com.wh.whtth.model.Product;
import com.wh.whtth.model.Shop;
import com.wh.whtth.model.User;
import com.wh.whtth.vo.ShopManageVo;
import com.wh.whtth.vo.ViewVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("adminService")
public class AdminService {
	
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
	
	public Map<String,String> addShopManager(ShopManageVo vo){
		Map<String,String> rtn = new HashMap<String,String>();
		int shopstate =0;
		int userstate = 0;
		if(userDao.selectByPhonenum(vo.getUser().getPhonenum())!=null){
			rtn.put("msg", "该手机号已被注册");
			return rtn;
		}
			
		if(userDao.selectByIdcard(vo.getUser().getIdcardNum())!=null){
			rtn.put("msg", "该身份证已被注册");
			return rtn;
		}
		User user = vo.getUser();
		Shop shop = vo.getShop();
		user.setState("1");
		user.setUserType("1");
		userstate = userDao.insert(user);
		//TODO 对商铺的收款链接预留
		vo.getShop().setUserid(userDao.selectByPhonenum(vo.getUser().getPhonenum()).getId());
		shop.setState(1);
		shopstate = shopDao.insert(vo.getShop());
		if(shopstate==0 && userstate==0 )
			rtn.put("msg", "添加失败");
		else
			rtn.put("msg", "添加成功");
		return rtn;
	}

	public List<Shop> listValidShops(ViewVo vo) {
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		return shopDao.listValidShops(vo);
	}

	public Object listInvalidShops(ViewVo vo) {
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		return shopDao.listInvalidShops(vo);
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
		Map<String,String> rtn = new HashMap<String,String>();
		int state = shopDao.updateByPrimaryKeySelective(shop);
		if(state > 0)
			rtn.put("msg", "更新成功");
		else
			rtn.put("msg", "更新失败");
		return rtn;
	}

	public Object checkPendingProducts(ViewVo vo) {
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		return productDao.checkPendingProducts(vo);
	}

	public Object approveAudit(String id) {
		Product pro = new Product();
		pro.setId(Long.parseLong(id));
		pro.setAuditstate("2");
		return edit(pro);
	}

	public Object edit(Product pro){
		Map<String,String> rtn = new HashMap<String,String>();
		int state = productDao.updateByPrimaryKeySelective(pro);
		if(state > 0)
			rtn.put("msg", "更新成功");
		else
			rtn.put("msg", "更新失败");
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
		Map<String,String> rtn = new HashMap<String,String>();
		if(userDao.selectByPhonenum(vo.getPhonenum())!=null){
			rtn.put("msg", "该手机号已被注册");
			return rtn;
		}
			
		if(userDao.selectByIdcard(vo.getIdcardNum())!=null){
			rtn.put("msg", "该身份证已被注册");
			return rtn;
		}
		vo.setState("1");
		vo.setUserType("2");
		int userstate = userDao.insert(vo);
		if(userstate==0 )
			rtn.put("msg", "添加失败");
		else
			rtn.put("msg", "添加成功");
		return rtn;
	}

	public Object makeUserInvalid(String id) {
		User user = new User();
		user.setId(Long.parseLong(id));
		user.setState("0");
		return edit(user);
	}

	public Object edit(User user){
		Map<String,String> rtn = new HashMap<String,String>();
		int state = userDao.updateByPrimaryKeySelective(user);
		if(state > 0)
			rtn.put("msg", "更新成功");
		else
			rtn.put("msg", "更新失败");
		return rtn;
	}

	public Object makeUserValid(String id) {
		User user = new User();
		user.setId(Long.parseLong(id));
		user.setState("1");
		return edit(user);
	}

	public Object recharge(String id, String money) {
		Map<String,String> rtn = new HashMap<String,String>();
		User user = userDao.selectByPrimaryKey(Long.parseLong(id));
		double balance = user.getBalance()+Integer.parseInt(money);
		user.setBalance(balance);
		int state = userDao.updateByPrimaryKeySelective(user);
		if(state > 0)
			rtn.put("msg", "充值成功");
		else
			rtn.put("msg", "充值失败");
		return rtn;
	}

	public Object userinfo(String id) {
		return userDao.selectByPrimaryKey(Long.parseLong(id));
	}

	public Object shoppingInfo(ViewVo vo) {
		return orderDao.shoppingInfo(vo);
	}


	public Object getTradeList() {
		return baseCodeDao.showTrade();
	}
}

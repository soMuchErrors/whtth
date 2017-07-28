package com.wh.whtth.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruibang.jedis.util.JedisUtil;
import com.wh.whtth.idao.BaseCodeMapper;
import com.wh.whtth.idao.CityMapper;
import com.wh.whtth.idao.FinanceLogMapper;
import com.wh.whtth.idao.OrderDetailsMapper;
import com.wh.whtth.idao.OrderMapper;
import com.wh.whtth.idao.ProductMapper;
import com.wh.whtth.idao.ProductSortMapper;
import com.wh.whtth.idao.ShippingAddressMapper;
import com.wh.whtth.idao.ShopMapper;
import com.wh.whtth.idao.ShoppingCartMapper;
import com.wh.whtth.idao.TranscationInfoMapper;
import com.wh.whtth.idao.UserMapper;
import com.wh.whtth.model.FinanceLog;
import com.wh.whtth.model.Order;
import com.wh.whtth.model.OrderDetails;
import com.wh.whtth.model.ShippingAddress;
import com.wh.whtth.model.Shop;
import com.wh.whtth.model.ShoppingCart;
import com.wh.whtth.model.TranscationInfo;
import com.wh.whtth.model.User;
import com.wh.whtth.util.DateUtil;
import com.wh.whtth.util.EncryptUtil;
import com.wh.whtth.vo.Message;
import com.wh.whtth.vo.OrderDetail;
import com.wh.whtth.vo.ShopInfoVo;
import com.wh.whtth.vo.SortProductVo;
import com.wh.whtth.vo.SortVo;
import com.wh.whtth.vo.ViewVo;

@Service("userService")
public class UserService {

	@Resource(name = "baseCodeDao")
	private BaseCodeMapper baseCodeDao;

	@Resource(name = "shopDao")
	private ShopMapper shopDao;

	@Resource(name = "productDao")
	private ProductMapper productDao;

	@Resource(name = "productSortDao")
	private ProductSortMapper productSortDao;

	@Resource(name = "shoppingCartDao")
	private ShoppingCartMapper shoppingCartDao;

	@Resource(name = "orderDao")
	private OrderMapper orderDao;

	@Resource(name = "orderDetailsDao")
	private OrderDetailsMapper orderDetailsDao;

	@Resource(name = "userDao")
	private UserMapper userDao;

	@Resource(name = "cityDao")
	private CityMapper cityDao;

	@Resource(name = "shippingAddressDao")
	private ShippingAddressMapper shippingAddressDao;

	@Resource(name = "transcationInfoDao")
	private TranscationInfoMapper transcationInfoDao;

	@Resource(name = "financeLogDao")
	private FinanceLogMapper financeLogDao;

	public Object showTrade() {
		return baseCodeDao.showTrade();
	}

	public Object showShops(ViewVo vo) {
		vo.setStart(vo.getPagesize() * (vo.getPage() - 1));
		vo.setEnd(vo.getPagesize() * vo.getPage());
		return shopDao.showShops(vo);
	}

	public Object getShopinfo(String id) {
		Map<String, Object> rst = new HashMap<String, Object>();
		ShopInfoVo shopinfo = new ShopInfoVo();
		shopinfo.setShop(shopDao.selectByPrimaryKey(Long.parseLong(id)));
		List<SortProductVo> sortProducts = new ArrayList<SortProductVo>();
		shopinfo.setSortProducts(sortProducts);
		List<Map<String, Object>> sorts = productSortDao.listProductSort(id);
		for (int i = 0; i < sorts.size(); i++) {
			List<Map<String, String>> products = productDao
					.listValidProductById(id, sorts.get(i).get("id").toString());
			if (products.size() == 0)
				continue;

			SortProductVo sortProduct = new SortProductVo();
			SortVo sort = new SortVo();
			sort.setId(sorts.get(i).get("id").toString());
			sort.setName(sorts.get(i).get("sortname").toString());
			sortProduct.setSort(sort);
			sortProduct.setProducts(products);
			sortProducts.add(sortProduct);
		}
		rst.put("obj", shopinfo);
		rst.put("message", new Message("1"));
		return rst;
	}

	public Object getShopinfoBySortid(String id, String sortid) {
		return productDao.getShopinfoBySortid(id, sortid);
	}

	public Object getProductinfoById(String id) {
		return productDao.selectByPrimaryKey(Long.parseLong(id));
	}

	public Object addShoppingCart(ShoppingCart vo) {
		Map<String, Object> rst = new HashMap<String, Object>();
		int insertState = 0;
		insertState = shoppingCartDao.insertSelective(vo);
		if (insertState == 0)
			rst.put("message", new Message("1", "添加失败"));
		else
			rst.put("message", new Message("1", "添加成功"));
		return rst;
	}

	public Object shoppingCartInThis(String userid, String shopid) {
		return shoppingCartDao.shoppingCartInThis(userid, shopid);
	}

	public Object privateShoppingCart(String userid) {
		return shoppingCartDao.privateShoppingCart(userid);
	}

	public Object createOrder(Map<String, String> vo) {
		Map<String, Object> rst = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int orderid = orderDao.getNextval();
		Order order = new Order();
		order.setId((long) orderid);
		order.setCreatetime(sdf.format(new Date(System.currentTimeMillis())));
		order.setShopid(Long.parseLong(vo.get("shopid")));
		orderDao.insertSelective(order);

		OrderDetails detail = new OrderDetails();
		detail.setOrderid((long) orderid);
		detail.setProductid(Long.parseLong(vo.get("productid")));
		detail.setNum(Integer.parseInt(vo.get("num")));
		orderDetailsDao.insertSelective(detail);

		int account = orderDao.getAccount(orderid + "");
		order.setAmount((double) account);
		order.setState(0);
		order.setUserid(Long.parseLong(vo.get("userid")));
		order.setShopid(Long.parseLong(vo.get("shopid")));
		orderDao.updateByPrimaryKeySelective(order);

		rst.put("message", new Message("1", "订单生成成功"));
		return rst;
	}

	// 设置会员名
	public Object editNickname(String id, String nickname) {
		User user = new User();
		user.setId(Long.parseLong(id));
		user.setNickname(nickname);
		return editUser(user);
	}

	// 是否有支付密码
	public Object hasPaypassword(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userDao.selectByPrimaryKey(Long.parseLong(id)).getPaypassword() == null) {
			map.put("hasPaypassword", "0");
		} else {
			map.put("hasPaypassword", "1");
		}
		map.put("message", new Message("1"));
		return map;
	}

	public Object editUser(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userDao.updateByPrimaryKeySelective(user) == 0) {
			map.put("message", new Message("0", "更新失败！"));
		} else {
			map.put("message", new Message("1", "更新成功！"));
		}
		return map;
	}

	public Object editPaypassword(String id, String oldpass, String newpass) {
		User user = new User();
		user.setId(Long.parseLong(id));
		if (oldpass != null
				&& userDao.selectByPrimaryKey(Long.parseLong(id))
						.getPaypassword() != Integer.parseInt(oldpass)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", new Message("0", "支付密码错误！"));
			return map;
		}
		user.setPaypassword(Integer.parseInt(newpass));
		return editUser(user);
	}

	public Object editPassword(String id, String oldpass, String newpass) {
		User user = new User();
		user.setId(Long.parseLong(id));
		if (oldpass == null
				|| !userDao.selectByPrimaryKey(Long.parseLong(id))
						.getPassword().equals(EncryptUtil.PBEEncrypt(oldpass))) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("message", new Message("0", "密码错误！"));
			return map;
		}
		user.setPassword(newpass);
		return editUser(user);
	}

	public Object listProvince() {
		Map<String, Object> rst = new HashMap<String, Object>();
		rst.put("list", cityDao.listProvince());
		rst.put("message", new Message("1"));
		return rst;
	}

	public Object listCityUnderProv(String code) {
		Map<String, Object> rst = new HashMap<String, Object>();
		rst.put("list", cityDao.listCityUnderProv(code));
		rst.put("message", new Message("1"));
		return rst;
	}

	public Object addShippingAddress(ShippingAddress vo) {
		Map<String, Object> rst = new HashMap<String, Object>();
		if (shippingAddressDao.insertSelective(vo) == 0) {
			rst.put("message", new Message("0", "插入失败"));
		} else {
			rst.put("message", new Message("1", "插入成功"));
		}
		return rst;
	}

	public Object listShippingAddress(String userid) {
		Map<String, Object> rst = new HashMap<String, Object>();
		rst.put("list", shippingAddressDao.listShippingAddress(userid));
		rst.put("message", new Message("1"));
		return rst;
	}

	public Object editShippingAddress(ShippingAddress vo) {
		Map<String, Object> rst = new HashMap<String, Object>();
		if (shippingAddressDao.updateByPrimaryKeySelective(vo) == 0) {
			rst.put("message", new Message("0", "更新失败"));
		} else {
			rst.put("message", new Message("1", "更新成功"));
		}
		return rst;
	}

	public Object unpaidOrder(ViewVo vo) {
		Map<String, Object> rst = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", vo.getId());
		params.put("state", 0);
		params.put("start", vo.getPagesize() * (vo.getPage() - 1));
		params.put("end", vo.getPagesize() * vo.getPage());
		rst.put("list", listOrders(params));
		rst.put("message", new Message("1"));
		return rst;
	}

	public Object unsentOrder(ViewVo vo) {
		Map<String, Object> rst = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", vo.getId());
		params.put("state", 1);
		params.put("start", vo.getPagesize() * (vo.getPage() - 1));
		params.put("end", vo.getPagesize() * vo.getPage());
		rst.put("list", listOrders(params));
		rst.put("message", new Message("1"));
		return rst;
	}

	public Object unconfirmOrder(ViewVo vo) {
		Map<String, Object> rst = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", vo.getId());
		params.put("state", 1);
		params.put("start", vo.getPagesize() * (vo.getPage() - 1));
		params.put("end", vo.getPagesize() * vo.getPage());
		rst.put("list", listOrders(params));
		rst.put("message", new Message("1"));
		return rst;
	}

	// 根据状态查询订单列表
	/**
	 * id userid state start end
	 */
	public Map<String, Object> listOrders(Map<String, Object> params) {
		return orderDao.listOrders(params);
	}

	public Object orderInfo(String id) {
		Map<String, Object> rst = new HashMap<String, Object>();
		OrderDetail order = new OrderDetail();
		order.setOrder(orderDao.SelectOrderinfoById(id));
		order.setGoods(orderDao.getDetailsById(id));
		rst.put("obj", order);
		rst.put("message", new Message("1"));
		return rst;
	}

	public Object salesReturn(String orderid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("obj", orderDao.selectByPrimaryKey(Long.parseLong(orderid)));
		map.put("message", new Message("1"));
		return map;
	}

	@Transactional
	public Object payment(String orderid, String paypassword) {
		Map<String, Object> map = new HashMap<String, Object>();
		Order order = orderDao.selectByPrimaryKey(Long.parseLong(orderid));
		long userid = order.getUserid();

		Shop shopinfo = shopDao.selectByPrimaryKey(order.getShopid());
		// 1.验证支付密码
		if (paypassword == null
				|| userDao.selectByPrimaryKey(userid).getPaypassword() != Integer
						.parseInt(paypassword)) {
			map.put("message", new Message("0", "支付密码错误！"));
			return map;
		}

		// 2.支付密码验证通过 给用户账户扣款
		User user = new User();
		user.setId(userid);
		double newbalance = userDao.selectByPrimaryKey(userid).getBalance()
				- order.getAmount();
		if (newbalance < 0) {
			map.put("message", new Message("0", "余额不足，请先充值！"));
			return map;
		}
		user.setBalance(newbalance);
		userDao.updateByPrimaryKeySelective(user);
		// 财务记录表插入数据
		FinanceLog flog = new FinanceLog();
		flog.setCashiermode(1);
		flog.setUserid(userid);
		flog.setMoney(order.getAmount());
		flog.setTimestamp(System.currentTimeMillis());
		String ymd = DateUtil.formatYmd().replace("-", "");
		flog.setYmd(ymd);
		flog.setYm(ymd.substring(0, 6));
		flog.setYear(ymd.substring(0, 4));
		flog.setOrderid(order.getId());
		flog.setLoginfo("在"+shopinfo.getName()+"消費"+order.getAmount()+"元");
		financeLogDao.insertSelective(flog);
		
		// 給商家划账 扣除折扣价
		User shopuser = new User();
		long shoperid = shopinfo.getUserid();
		shopuser.setId(shoperid);
		double money = order.getAmount() * shopinfo.getPushmoney() / 100;
		newbalance = userDao.selectByPrimaryKey(shoperid)
				.getBalance()
				+ order.getAmount()
				* shopinfo.getPushmoney()
				/ 100;
		shopuser.setBalance(newbalance);
		userDao.updateByPrimaryKeySelective(shopuser);
		// 财务记录表插入数据
		flog.setCashiermode(0);
		flog.setUserid(shoperid);
		flog.setMoney(money);
		flog.setTimestamp(System.currentTimeMillis());
		flog.setYmd(ymd);
		flog.setYm(ymd.substring(0, 6));
		flog.setYear(ymd.substring(0, 4));
		flog.setOrderid(order.getId());
		flog.setLoginfo("来自订单："+order.getId()+"。收入"+money+"元");
		financeLogDao.insertSelective(flog);
		
		//记录交易信息表
		TranscationInfo offlineOrder = new TranscationInfo();
		offlineOrder.setBuyer(userid);
		offlineOrder.setSeller(shoperid);
		offlineOrder.setAmount(order.getAmount());
		offlineOrder.setTimestamp(System.currentTimeMillis());
		offlineOrder.setYmd(ymd);
		offlineOrder.setYm(ymd.substring(0, 6));
		offlineOrder.setYear(ymd.substring(0, 4));
		offlineOrder.setType(1);
		offlineOrder.setOrderid(order.getId());
		transcationInfoDao.insertSelective(offlineOrder);
		
		map.put("message", new Message("1", "支付成功！"));
		return map;
	}

	public Object confirmReturn(Map<String, String> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		Order order = new Order();
		order.setId(Long.parseLong(params.get("orderid")));
		order.setReason(params.get("reason"));
		order.setState(5);
		orderDao.updateByPrimaryKeySelective(order);
		map.put("message", new Message("1"));
		return map;
	}

	public Object recharge(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object qrCodePay(Map<String, String> params) {
		Map<String, Object> map = new HashMap<String, Object>();

		String buyer = params.get("from");
		String seller = params.get("to");
		Map<String, String> obj = shopDao.selectByUserid(seller);

		String key = System.currentTimeMillis() + "";
		obj.put("orderid", key);

		JedisUtil.hset(key, "buyer", buyer);
		JedisUtil.hset(key, "seller", seller);
		JedisUtil.hset(key, "flag", "0");
		JedisUtil.expire(key, 300);

		map.put("obj", obj);
		map.put("message", new Message("1"));
		return map;
	}

	@Transactional
	public Object qrCodePayConfirm(String token, Map<String, String> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (params.size() != 3) {
			map.put("message", new Message("2", "非法请求。"));
		}

		long userid = Long.parseLong(JedisUtil.hget(token, "userid"));
		String orderid = params.get("orderid");
		String buyer = JedisUtil.hget(orderid, "buyer");
		String seller = JedisUtil.hget(orderid, "seller");
		String paypassword = params.get("paypassword");
		// 1.验证支付密码
		if (paypassword == null
				|| userDao.selectByPrimaryKey(userid).getPaypassword() != Integer
						.parseInt(paypassword)) {
			map.put("message", new Message("0", "支付密码错误！"));
			return map;
		}

		// 2.验证订单有效性 a。订单未过期 b。订单未成交
		if (!JedisUtil.exists(orderid)
				|| JedisUtil.hincrBy(orderid, "flag", 1) > 1
				|| !JedisUtil.hget(orderid, "buyer").equals(buyer)
				|| !JedisUtil.hget(orderid, "seller").equals(seller)) {
			map.put("message", new Message("0", "无效订单！"));
			return map;
		}

		double amount = Double.parseDouble(params.get("amount"));

		// 3.扣款
		User user = new User();
		user.setId(userid);
		double newbalance = userDao.selectByPrimaryKey(userid).getBalance()
				- amount;
		if (newbalance < 0) {
			map.put("message", new Message("0", "余额不足，请先充值！"));
			return map;
		}
		user.setBalance(newbalance);
		userDao.updateByPrimaryKeySelective(user);
		// 财务记录表插入数据
		FinanceLog flog = new FinanceLog();
		flog.setCashiermode(1);
		flog.setUserid(userid);
		flog.setMoney(amount);
		flog.setTimestamp(System.currentTimeMillis());
		String ymd = DateUtil.formatYmd().replace("-", "");
		flog.setYmd(ymd);
		flog.setYm(ymd.substring(0, 6));
		flog.setYear(ymd.substring(0, 4));
		flog.setLoginfo("线下消费"+amount+"元");
		financeLogDao.insertSelective(flog);

		// 給商家划账 扣除折扣价
		User shopuser = new User();
		shopuser.setId(Long.parseLong(seller));
		double money = amount
				* shopDao.selectByUserid(Long.parseLong(seller)).getPushmoney()
				/ 100;
		double abalance = userDao.selectByPrimaryKey(Long.parseLong(seller))
				.getBalance() + money;
		shopuser.setBalance(abalance);
		userDao.updateByPrimaryKeySelective(shopuser);
		// 财务记录表插入数据
		flog.setCashiermode(0);
		flog.setUserid(Long.parseLong(seller));
		flog.setMoney(money);
		flog.setTimestamp(System.currentTimeMillis());
		flog.setYmd(ymd);
		flog.setYm(ymd.substring(0, 6));
		flog.setYear(ymd.substring(0, 4));
		flog.setLoginfo("线下收款"+money+"元");
		financeLogDao.insertSelective(flog);
		
		//记录交易信息表
		TranscationInfo offlineOrder = new TranscationInfo();
		offlineOrder.setBuyer(Long.parseLong(buyer));
		offlineOrder.setSeller(Long.parseLong(seller));
		offlineOrder.setAmount(amount);
		offlineOrder.setTimestamp(System.currentTimeMillis());
		offlineOrder.setYmd(ymd);
		offlineOrder.setYm(ymd.substring(0, 6));
		offlineOrder.setYear(ymd.substring(0, 4));
		offlineOrder.setType(2);
		transcationInfoDao.insertSelective(offlineOrder);
		
		map.put("message", new Message("1", "支付成功！"));
		return map;
	}

	public Object consumptionStatus(String userid, String ym) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", financeLogDao.consumptionStatus(userid,ym));
		return map;
	}
}

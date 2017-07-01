package com.wh.whtth.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wh.whtth.idao.BaseCodeMapper;
import com.wh.whtth.idao.OrderDetailsMapper;
import com.wh.whtth.idao.OrderMapper;
import com.wh.whtth.idao.ProductMapper;
import com.wh.whtth.idao.ProductSortMapper;
import com.wh.whtth.idao.ShopMapper;
import com.wh.whtth.idao.ShoppingCartMapper;
import com.wh.whtth.model.Order;
import com.wh.whtth.model.OrderDetails;
import com.wh.whtth.model.Product;
import com.wh.whtth.model.ProductSort;
import com.wh.whtth.model.ShoppingCart;
import com.wh.whtth.vo.ShopInfoVo;
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
	
	public Object showTrade() {
		return baseCodeDao.showTrade();
	}

	public Object showShops(ViewVo vo) {
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		return shopDao.showShops(vo);
	}

	public Object getShopinfo(String id) {
		ShopInfoVo shopinfo = new ShopInfoVo();
		shopinfo.setShop(shopDao.selectByPrimaryKey(Long.parseLong(id)));
		List<ProductSort> sorts = productSortDao.listProductSort(id);
		List<Map<String,Map<String,String>>> productinfos = new ArrayList<Map<String,Map<String,String>>>();
		for(int i=0;i<sorts.size();i++){
			List<Map<String,String>> products = productDao.listValidProductById(id,sorts.get(i).getId()+"");
			Map<String,Map<String,String>> tempMap = new HashMap<String,Map<String,String>>();
			String tempkey = sorts.get(i).getId() + "_" + sorts.get(i).getSortname();
			for(int j=0;j<products.size();j++){
				tempMap.put(tempkey, products.get(j));
			}
			productinfos.add(tempMap);
		}
		return productinfos;
	}

	public Object getShopinfoBySortid(String id, String sortid) {
		return productDao.getShopinfoBySortid(id, sortid);
	}

	public Object getProductinfoById(String id) {
		return productDao.selectByPrimaryKey(Long.parseLong(id));
	}

	public Object addShoppingCart(List<ShoppingCart> vo) {
		Map<String,String> rst = new HashMap<String,String>();
		int insertState=0;
		for(ShoppingCart sc : vo){
			insertState = shoppingCartDao.insertSelective(sc);
		}
		if(insertState == 0)
			rst.put("msg", "添加失败");
		else
			rst.put("msg", "添加成功");
		return rst;
	}

	public Object shoppingCartInThis(String userid, String shopid) {
		return shoppingCartDao.shoppingCartInThis(userid, shopid);
	}

	public Object privateShoppingCart(String userid) {
		return shoppingCartDao.privateShoppingCart(userid);
	}

	public Object createOrder(List<Map<String,String>> vo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int orderid = orderDao.getNextval();
		Order order = new Order();
		order.setId((long)orderid);
		order.setCreatetime(sdf.format(new Date(System.currentTimeMillis())));
		order.setShopid(Long.parseLong(vo.get(0).get("shopid")));
		orderDao.insertSelective(order);
		for(int i=0;i<vo.size();i++){
			OrderDetails detail = new OrderDetails();
			detail.setOrderid((long)orderid);
			detail.setProductid(Long.parseLong(vo.get(i).get("productid")));
			detail.setNum(Integer.parseInt(vo.get(i).get("num")));
			orderDetailsDao.insertSelective(detail);
		}
		int account = orderDao.getAccount(orderid+"");
		order.setAmount((double)account);
		order.setState(0);
		order.setUserid(Long.parseLong(vo.get(0).get("userid")));
		order.setShopid(Long.parseLong(vo.get(0).get("shopid")));
		orderDao.updateByPrimaryKeySelective(order);
		Map<String,String> rst = new HashMap<String,String>();
		rst.put("msg", "订单生成成功");
		return rst;
	}
	
}

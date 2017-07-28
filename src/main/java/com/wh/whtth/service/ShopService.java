package com.wh.whtth.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wh.whtth.idao.OrderMapper;
import com.wh.whtth.idao.ProductMapper;
import com.wh.whtth.idao.ProductSortMapper;
import com.wh.whtth.idao.ShopMapper;
import com.wh.whtth.idao.UserMapper;
import com.wh.whtth.model.Order;
import com.wh.whtth.model.Product;
import com.wh.whtth.model.ProductSort;
import com.wh.whtth.model.Shop;
import com.wh.whtth.model.User;
import com.wh.whtth.vo.Message;
import com.wh.whtth.vo.OrderDetail;
import com.wh.whtth.vo.UnderLineOrderVo;
import com.wh.whtth.vo.ViewVo;

@Service("shopService")
public class ShopService {
	@Resource(name = "productDao")
	private ProductMapper productDao;
	
	@Resource(name = "productSortDao")
	private ProductSortMapper productSortDao;
	
	@Resource(name = "shopDao")
	private ShopMapper shopDao;
	
	@Resource(name = "orderDao")
	private OrderMapper orderDao;
	
	@Resource(name = "userDao")
	private UserMapper userDao;
	
	public Object addProduct(Product vo) {
		Map<String,Object> rst = new HashMap<String,Object>();
		vo.setState(1);
		vo.setAuditstate("1");
		int state = productDao.insertSelective(vo);
		if(state == 0)
			rst.put("message", new Message("0","上架失败"));
		else
			rst.put("message", new Message("1","上架成功"));
		return rst;
	}
	
	
	//添加商品分类
	public Object addProductSort(ProductSort vo) {
		Map<String,Object> rst = new HashMap<String,Object>();
		int insertState = productSortDao.insertSelective(vo);
		if(insertState == 0)
			rst.put("message", new Message("0","添加失败"));
		else
			rst.put("message", new Message("1","添加成功"));
		return rst;
	}


	public Object delProductSort(String id) {
		Map<String,Object> rst = new HashMap<String,Object>();
		if(productDao.checkProductBySort(id)>0){
			rst.put("message", new Message("0","该分类下还有产品，不能删除"));
			return rst;
		}
			
		int insertState = productSortDao.deleteByPrimaryKey(Long.parseLong(id));
		if(insertState == 0)
			rst.put("message", new Message("0","删除失败"));
		else
			rst.put("message", new Message("1","删除成功"));
		return rst;
	}
	

	//展示所有分类
	public Object listProductSort(String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> list = productSortDao.listProductSort(id);
		map.put("list", list);
		map.put("message", new Message("1"));
		return map;
	}

	//查看上架商品列表
	public Object listValidProduct(ViewVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		vo.setEnd(vo.getPagesize()*vo.getPage());
		List<Map<String,String>> list = productDao.listValidProduct(vo);
		map.put("list", list);
		map.put("page", vo.getPage());
		map.put("total", productDao.countValidProduct(vo.getId()));
		map.put("message", new Message("1"));
		return map;
	}


	public Object listInvalidProduct(ViewVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		vo.setEnd(vo.getPagesize()*vo.getPage());
		List<Map<String,String>> list = productDao.listInvalidProduct(vo);
		map.put("list", list);
		map.put("page", vo.getPage());
		map.put("total", productDao.countInvalidProduct(vo.getId()));
		map.put("message", new Message("1"));
		return map;
	}


	public Object makeProductInvalid(String id) {
		Map<String,Object> rst = new HashMap<String,Object>();
		Product pro = productDao.selectByPrimaryKey(Long.parseLong(id));
		pro.setState(0);
		int State = productDao.updateByPrimaryKeySelective(pro);
		if(State == 0)
			rst.put("message", new Message("0","下架失败"));
		else
			rst.put("message", new Message("1","下架成功"));
		return rst;
	}


	public Object makeProductValid(String id) {
		Map<String,Object> rst = new HashMap<String,Object>();
		Product pro = productDao.selectByPrimaryKey(Long.parseLong(id));
		pro.setState(1);
		int State = productDao.updateByPrimaryKeySelective(pro);
		if(State == 0)
			rst.put("message", new Message("0","上架失败"));
		else
			rst.put("message", new Message("1","上架成功"));
		return rst;
	}


	public Object delProduct(String id) {
		Map<String,Object> rst = new HashMap<String,Object>();
		int State = productDao.deleteByPrimaryKey(Long.parseLong(id));
		if(State == 0)
			rst.put("message", new Message("0","删除失败"));
		else
			rst.put("message", new Message("1","删除成功"));
		return rst;
	}


	public Object viewProduct(String id) {
		return productDao.selectByPrimaryKey(Long.parseLong(id));
	}


	public Object editProduct(Product vo) {
		Map<String,Object> rst = new HashMap<String,Object>();
		int State = productDao.updateByPrimaryKeySelective(vo);
		if(State == 0)
			rst.put("message", new Message("0","更新失败"));
		else
			rst.put("message", new Message("1","更新成功"));
		return rst;
	}


	public Object showinfo(String id) {
		return shopDao.showinfo(id);
	}


	public Object showOrders(ViewVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		vo.setEnd(vo.getPagesize()*vo.getPage());
		List<Map<String,String>> list = orderDao.showOrders(vo);
		map.put("list", list);
		map.put("page", vo.getPage());
		map.put("total", orderDao.countOrders(vo.getId()));
		map.put("message", new Message("1"));
		return map;
//		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
	}


	public Object showOrderDetails(String id) {
		OrderDetail order = new OrderDetail();
		order.setOrder(orderDao.SelectOrderinfoById(id));
		order.setGoods(orderDao.getDetailsById(id));
		return order;
	}


	public Object deliver(String id) {
		Map<String,Object> rst = new HashMap<String,Object>();
		Order vo = orderDao.selectByPrimaryKey(Long.parseLong(id));
		vo.setState(2);
		int State = orderDao.updateByPrimaryKeySelective(vo);
		if(State == 0)
			rst.put("message", new Message("0","发货失败"));
		else
			rst.put("message", new Message("1","发货成功"));
		return rst;
	}


	public Object underlineOrder(Order vo) {
		Map<String,Object> rst = new HashMap<String,Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setCreatetime(sdf.format(new Date(System.currentTimeMillis())));
		vo.setState(1);
		int State = orderDao.insertSelective(vo);
		if(State == 0)
			rst.put("message", new Message("0","订单生成失败"));
		else
			rst.put("message", new Message("1","订单生成成功"));
		return rst;
	}


	public Object editShopinfo(Map<String, String> vo) {
		Map<String,Object> rst = new HashMap<String,Object>();
		User user = new User();
		user.setId(Long.parseLong(vo.get("userid")));
		user.setEmail(vo.get("email"));
		Shop shop = new Shop();
		shop.setId(Long.parseLong(vo.get("shopid")));
		shop.setAddress(vo.get("address"));
		shop.setPicture(vo.get("picture"));
		shop.setTelphone(vo.get("telphone"));
		shop.setShopdesc(vo.get("shopdesc"));
		userDao.updateByPrimaryKeySelective(user);
		shopDao.updateByPrimaryKeySelective(shop);
		rst.put("message", new Message("1","保存成功"));
		return rst;
	}

}

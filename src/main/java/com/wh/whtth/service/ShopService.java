package com.wh.whtth.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wh.whtth.idao.OrderMapper;
import com.wh.whtth.idao.ProductMapper;
import com.wh.whtth.idao.ProductSortMapper;
import com.wh.whtth.idao.ShopMapper;
import com.wh.whtth.model.Order;
import com.wh.whtth.model.Product;
import com.wh.whtth.model.ProductSort;
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
	
	public Object addProduct(Product vo) {
		Map<String,String> rst = new HashMap<String,String>();
		vo.setState(1);
		vo.setAuditstate("1");
		int state = productDao.insertSelective(vo);
		if(state == 0)
			rst.put("msg", "上架失败");
		else
			rst.put("msg", "上架成功");
		return rst;
	}
	
	
	//添加商品分类
	public Object addProductSort(ProductSort vo) {
		Map<String,String> rst = new HashMap<String,String>();
		int insertState = productSortDao.insertSelective(vo);
		if(insertState == 0)
			rst.put("msg", "添加失败");
		else
			rst.put("msg", "添加成功");
		return rst;
	}


	public Object delProductSort(String id) {
		Map<String,String> rst = new HashMap<String,String>();
		if(productDao.checkProductBySort(id)>0){
			rst.put("msg", "该分类下还有产品，不能删除");
			return rst;
		}
			
		int insertState = productSortDao.deleteByPrimaryKey(Long.parseLong(id));
		if(insertState == 0)
			rst.put("msg", "删除失败");
		else
			rst.put("msg", "删除成功");
		return rst;
	}


	public Object listProductSort(String id) {
		return productSortDao.listProductSort(id);
	}

	//查看上架商品列表
	public Object listValidProduct(ViewVo vo) {
		return productDao.listValidProduct(vo);
	}


	public Object listInvalidProduct(ViewVo vo) {
		return productDao.listInvalidProduct(vo);
	}


	public Object makeProductInvalid(String id) {
		Map<String,String> rst = new HashMap<String,String>();
		Product pro = productDao.selectByPrimaryKey(Long.parseLong(id));
		pro.setState(0);
		int State = productDao.updateByPrimaryKeySelective(pro);
		if(State == 0)
			rst.put("msg", "下架失败");
		else
			rst.put("msg", "下架成功");
		return rst;
	}


	public Object makeProductValid(String id) {
		Map<String,String> rst = new HashMap<String,String>();
		Product pro = productDao.selectByPrimaryKey(Long.parseLong(id));
		pro.setState(1);
		int State = productDao.updateByPrimaryKeySelective(pro);
		if(State == 0)
			rst.put("msg", "上架失败");
		else
			rst.put("msg", "上架成功");
		return rst;
	}


	public Object delProduct(String id) {
		Map<String,String> rst = new HashMap<String,String>();
		int State = productDao.deleteByPrimaryKey(Long.parseLong(id));
		if(State == 0)
			rst.put("msg", "删除失败");
		else
			rst.put("msg", "删除成功");
		return rst;
	}


	public Object viewProduct(String id) {
		return productDao.selectByPrimaryKey(Long.parseLong(id));
	}


	public Object editProduct(Product vo) {
		Map<String,String> rst = new HashMap<String,String>();
		int State = productDao.updateByPrimaryKeySelective(vo);
		if(State == 0)
			rst.put("msg", "删除失败");
		else
			rst.put("msg", "删除成功");
		return rst;
	}


	public Object showinfo(String id) {
		return shopDao.selectByPrimaryKey(Long.parseLong(id));
	}


	public Object showOrders(ViewVo vo) {
		vo.setStart(vo.getPagesize()*(vo.getPage()-1));
		return orderDao.showOrders(vo);
	}


	public Object showOrderDetails(String id) {
		OrderDetail order = new OrderDetail();
		order.setOrder(orderDao.SelectOrderinfoById(id));
		order.setGoods(orderDao.getDetailsById(id));
		return order;
	}


	public Object deliver(String id) {
		Map<String,String> rst = new HashMap<String,String>();
		Order vo = orderDao.selectByPrimaryKey(Long.parseLong(id));
		int State = orderDao.updateByPrimaryKeySelective(vo);
		if(State == 0)
			rst.put("msg", "发货失败");
		else
			rst.put("msg", "发货成功");
		return rst;
	}


	public Object underlineOrder(Order vo) {
		Map<String,String> rst = new HashMap<String,String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setCreatetime(sdf.format(new Date(System.currentTimeMillis())));
		int State = orderDao.insertSelective(vo);
		if(State == 0)
			rst.put("msg", "订单生成失败");
		else
			rst.put("msg", "订单生成成功");
		return rst;
	}

}

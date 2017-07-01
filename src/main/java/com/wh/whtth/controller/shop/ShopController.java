package com.wh.whtth.controller.shop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wh.whtth.controller.BaseController;
import com.wh.whtth.model.Order;
import com.wh.whtth.model.Product;
import com.wh.whtth.model.ProductSort;
import com.wh.whtth.service.ShopService;
import com.wh.whtth.vo.ShopManageVo;
import com.wh.whtth.vo.UnderLineOrderVo;
import com.wh.whtth.vo.ViewVo;

@Controller
@RequestMapping("/shop")
public class ShopController extends BaseController{
	
	@Autowired
	private ShopService shopService;
	
	//添加分类
	@RequestMapping(value="/productSort/addProductSort",method = RequestMethod.POST,headers = {})
	public void addProductSort(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ProductSort vo = (ProductSort)getVo(req, ProductSort.class);
		wrint(res, shopService.addProductSort(vo));
	}
	
	//删除分类
	@RequestMapping(value="/productSort/delProductSort",headers = {})
	public void delProductSort(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, shopService.delProductSort(req.getParameter("id")));
	}
	
	//查询分类
	@RequestMapping(value="/productSort/listProductSort",headers = {})
	public void listProductSort(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, shopService.listProductSort(req.getParameter("id")));
	}
	
	//上架产品
	@RequestMapping(value="/product/addProduct",method = RequestMethod.POST,headers = {})
	public void addShopManager(HttpServletRequest req,HttpServletResponse res) throws Exception{
		Product vo = (Product)getVo(req, Product.class);
		wrint(res, shopService.addProduct(vo));
	}
	
	//上架产品列表
	@RequestMapping(value="/product/listValidProduct",method = RequestMethod.POST,headers = {})
	public void listValidProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ViewVo vo = (ViewVo)getVo(req, ViewVo.class);
		wrint(res, shopService.listValidProduct(vo));
	}
	
	//下架产品列表
	@RequestMapping(value="/product/listInvalidProduct",method = RequestMethod.POST,headers = {})
	public void listInvalidProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ViewVo vo = (ViewVo)getVo(req, ViewVo.class);
		wrint(res, shopService.listInvalidProduct(vo));
	}
	
	//下架商品
	@RequestMapping(value="/product/makeProductInvalid",headers = {})
	public void makeProductInvalid(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, shopService.makeProductInvalid(req.getParameter("id")));
	}
	
	//上架商品
	@RequestMapping(value="/product/makeProductValid",headers = {})
	public void makeProductValid(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, shopService.makeProductValid(req.getParameter("id")));
	}
	
	//删除商品
	@RequestMapping(value="/product/delProduct",headers = {})
	public void delProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, shopService.delProduct(req.getParameter("id")));
	}
	
	//查看商品详情
	@RequestMapping(value="/product/viewProduct",headers = {})
	public void viewProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, shopService.viewProduct(req.getParameter("id")));
	}
	
	//编辑商品
	@RequestMapping(value="/product/editProduct",method = RequestMethod.POST,headers = {})
	public void editProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		Product vo = (Product)getVo(req, Product.class);
		wrint(res, shopService.editProduct(vo));
	}
	
	//店铺信息
	@RequestMapping(value="/shop/showinfo",headers = {})
	public void showinfo(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, shopService.showinfo(req.getParameter("id")));
	}
	
	//订单列表
	@RequestMapping(value="/order/showOrders",method = RequestMethod.POST,headers = {})
	public void showOrders(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ViewVo vo = (ViewVo)getVo(req, ViewVo.class);
		wrint(res, shopService.showOrders(vo));
	}
	
	//订单详情
	@RequestMapping(value="/order/showOrderDetails",headers = {})
	public void showOrderDetails(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, shopService.showOrderDetails(req.getParameter("id")));
	}
	
	//发货
	@RequestMapping(value="/order/deliver",headers = {})
	public void deliver(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, shopService.deliver(req.getParameter("id")));
	}
	
	//线下订单
	@RequestMapping(value="/order/underlineOrder",method = RequestMethod.POST,headers = {})
	public void underlineOrder(HttpServletRequest req,HttpServletResponse res) throws Exception{
		Order vo = (Order)getVo(req, Order.class);
		wrint(res, shopService.underlineOrder(vo));
	}
}

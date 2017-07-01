package com.wh.whtth.controller.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wh.whtth.controller.BaseController;
import com.wh.whtth.model.Order;
import com.wh.whtth.model.OrderDetails;
import com.wh.whtth.model.ShoppingCart;
import com.wh.whtth.service.UserService;
import com.wh.whtth.vo.ViewVo;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	//行业列表
	@RequestMapping(value="/basecode/showTrade",headers = {})
	public void showTrade(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.showTrade());
	}
	
	//店铺列表
	@RequestMapping(value="/shop/showShops",method = RequestMethod.POST,headers = {})
	public void showShops(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ViewVo vo = (ViewVo) getVo(req, ViewVo.class);
		wrint(res, userService.showShops(vo));
	}
	
	//店铺详情
	@RequestMapping(value="/shop/getShopinfo",headers = {})
	public void getShopinfo(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.getShopinfo(req.getParameter("id")));
	}
	
	//根据分类获取所有商品
	@RequestMapping(value="/shop/listProductBySortid",headers = {})
	public void listProductBySortid(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.getShopinfoBySortid(req.getParameter("id"),req.getParameter("sortid")));
	}
	
	//获取商品详情信息
	@RequestMapping(value="/shop/getProductinfoById",headers = {})
	public void getProductinfoById(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.getProductinfoById(req.getParameter("id")));
	}
	
	//加入购物车
	@RequestMapping(value="/shoppingCart/addShoppingCart",method = RequestMethod.POST,headers = {})
	public void addShoppingCart(HttpServletRequest req,HttpServletResponse res) throws Exception{
		List<ShoppingCart> vo = (List<ShoppingCart>) getVo(req, List.class);
		wrint(res, userService.addShoppingCart(vo));
	}
	
	//查看在本店的购物车信息
	@RequestMapping(value="/shoppingCart/shoppingCartInThis",headers = {})
	public void shoppingCartInThis(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.shoppingCartInThis(req.getParameter("userid"),req.getParameter("shopid")));
	}	
	
	//查看私人购物车  hai
	@RequestMapping(value="/shoppingCart/privateShoppingCart",headers = {})
	public void privateShoppingCart(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.privateShoppingCart(req.getParameter("userid")));
	}
	
	//生成订单
	@RequestMapping(value="/order/createOrder",method = RequestMethod.POST,headers = {})
	public void createOrder(HttpServletRequest req,HttpServletResponse res) throws Exception{
		List<Map<String,String>> vo = (List<Map<String,String>>) getVo(req, List.class);
		wrint(res, userService.createOrder(vo));
	}
}

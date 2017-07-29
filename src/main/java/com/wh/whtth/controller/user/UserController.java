package com.wh.whtth.controller.user;

import com.wh.whtth.controller.BaseController;
import com.wh.whtth.model.ShippingAddress;
import com.wh.whtth.model.ShoppingCart;
import com.wh.whtth.service.UserService;
import com.wh.whtth.util.DateUtil;
import com.wh.whtth.vo.ViewVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Value("${imgpath}")
	private String imgurl;
	
	@Value("${imgsubfix}")
	private String imgsubfix;
	
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
		ShoppingCart vo = (ShoppingCart) getVo(req, ShoppingCart.class);
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
		Map<String,String> vo = (Map<String,String>) getVo(req, Map.class);
		wrint(res, userService.createOrder(vo));
	}
	
	//待付款订单列表
	@RequestMapping(value="/order/unpaidOrder",headers = {})
	public void unpaidOrder(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ViewVo vo = (ViewVo) getVo(req, ViewVo.class);
		wrint(res, userService.unpaidOrder(vo));
	}
	
	//付款
	@RequestMapping(value="/order/payment",headers = {})
	public void payment(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.payment(req.getParameter("orderid"),req.getParameter("paypassword")));
	}
	
	//待发货订单列表
	@RequestMapping(value="/order/unsentOrder",method = RequestMethod.POST,headers = {})
	public void unsentOrder(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ViewVo vo = (ViewVo) getVo(req, ViewVo.class);
		wrint(res, userService.unsentOrder(vo));
	}
	
	//待确认订单列表
	@RequestMapping(value="/order/unconfirmOrder",method = RequestMethod.POST,headers = {})
	public void unconfirmOrder(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ViewVo vo = (ViewVo) getVo(req, ViewVo.class);
		wrint(res, userService.unconfirmOrder(vo));
	}
	
	//订单详情
	@RequestMapping(value="/order/orderInfo",headers = {})
	public void orderInfo(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.orderInfo(req.getParameter("orderid")));
	}
	
	//申请退货
	@RequestMapping(value="/order/salesReturn",headers = {})
	public void salesReturn(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.salesReturn(req.getParameter("orderid")));
	}
	
	//确认退货
	@RequestMapping(value="/order/confirmReturn",headers = {})
	public void confirmReturn(HttpServletRequest req,HttpServletResponse res,@RequestBody Map<String,String> params) throws Exception{
		wrint(res, userService.confirmReturn(params));
	}
	
	//设置头像
	/**
	 * 上传图片文件
	 * 
	 * @param req
	 * @param res
	 * @param img
	 * @return
	 */
	@RequestMapping(value = "/upload/image")
	public @ResponseBody String uploadImg(HttpServletRequest req,
			HttpServletResponse res, @RequestBody MultipartFile img)
			throws Exception {
		String originalFilename = img.getOriginalFilename();
		String newdir = "";
		String newFileName = "";
		if (img != null && !StringUtils.isEmpty(originalFilename)) {
			newdir= File.separator
					+"user"
					+ File.separator
					+ DateUtil.formatYm();
			newFileName = UUID.randomUUID()
					+ originalFilename.substring(originalFilename
							.lastIndexOf("."));
			File newFile = new File(imgurl + newdir);
			newFile.setWritable(true);
			if(!newFile.exists()){
				newFile.mkdirs();
			}
			newFile = new File(imgurl + newdir + File.separator +newFileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
				img.transferTo(newFile);
			}
		}
		return imgsubfix + newdir + File.separator +newFileName;
	}
	
	//设置会员名
	@RequestMapping(value="/user/editNickname",headers = {})
	public void editNickname(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.editNickname(req.getParameter("userid"),req.getParameter("nickname")));
	}
	
	//是否有支付密码
//	@RequestMapping(value="/user/hasPaypassword",headers = {})
//	public void hasPaypassword(HttpServletRequest req,HttpServletResponse res) throws Exception{
//		wrint(res, userService.hasPaypassword(req.getParameter("userid")));
//	}
	
	/**
	 * 扫描支付全过程
	 * 1.客服端发起扫描二维码
	 * 2.识别二维码 拿到 shoperid，以及对应接口地址   以POST形式调用接口，
	 * 3.系统缓存开启临时账单   返回店铺名称  店铺头像 账单id 
	 */
	//扫码支付
	@RequestMapping(value="/user/qrCodePay",method = RequestMethod.POST,headers = {})
	public void qrCodePay(HttpServletRequest req,HttpServletResponse res,@RequestBody Map<String,String> params) throws Exception{
		wrint(res, userService.qrCodePay(params));
	}
	
	/**
	 * 扫码支付确认发生时间：输入支付金额，且输入支付密码
	 * params
	 * 		orderid
	 * 		amount
	 * 		paypassword
	 */
	//扫描支付确认支付
	@RequestMapping(value="/user/qrCodePayConfirm",method = RequestMethod.POST,headers = {})
	public void qrCodePayConfirm(HttpServletRequest req,HttpServletResponse res,@RequestBody Map<String,String> params) throws Exception{
		wrint(res, userService.qrCodePayConfirm(req.getParameter("token"),params));
	}
	
	//设置支付密码
	/**
	 * 
	 * @param req  userid oldpass 旧支付密码  newpass 新支付密码
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/user/editPaypassword",headers = {})
	public void editPaypassword(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.editPaypassword(req.getParameter("userid"),req.getParameter("oldpass"),req.getParameter("newpass")));
	}
	
	//修改登陆密码
	/**
	 * 
	 * @param req  userid oldpass 旧密码  newpass 新密码
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/user/editPassword",headers = {})
	public void editPassword(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.editPassword(req.getParameter("userid"),req.getParameter("oldpass"),req.getParameter("newpass")));
	}
	
	/**
	 * 充值：手机客户端充值成功以后调用接口
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 */
	//TODO
	@RequestMapping(value="/user/recharge",method = RequestMethod.POST,headers = {})
	public void recharge(HttpServletRequest req,HttpServletResponse res,@RequestBody Map<String,String> params) throws Exception{
		wrint(res, userService.recharge(params));
	}
	
	//报表模块
	//1.过去5个月的消费情况
	@RequestMapping(value="/user/consumptionStatus",headers = {})
	public void consumptionStatus(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.consumptionStatus(req.getParameter("userid"),req.getParameter("ym")));
	}
	
	//2.消费结构分析
	@RequestMapping(value="/user/consumptionAnalyze",headers = {})
	public void consumptionAnalyze(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.consumptionAnalyze(req.getParameter("userid"),req.getParameter("ym")));
	}
	
	//3.消费明细
	@RequestMapping(value="/user/consumptionDetail",method = RequestMethod.POST,headers = {})
	public void consumptionDetail(HttpServletRequest req,HttpServletResponse res,@RequestBody Map<String,String> params) throws Exception{
		wrint(res, userService.consumptionDetail(params));
	}
	
	/** ========================================================================================================= */
	
	//获取省列表
	@RequestMapping(value="/city/listProvince",headers = {})
	public void listProvince(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.listProvince());
	}
	
	//根据省编码获取下属地市列表
	@RequestMapping(value="/city/listCityUnderProv",headers = {})
	public void listCityUnderProv(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.listCityUnderProv(req.getParameter("code")));
	}
	
	//地址管理
	@RequestMapping(value="/shippingAddress/listShippingAddress",headers = {})
	public void listShippingAddress(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, userService.listShippingAddress(req.getParameter("userid")));
	}
	
	//新增地址管理
	/**
	 * 
	 * @param 
	 * userid 
	 * province
	 * city
	 * street
	 * fulladdress
	 * name
	 * telphone
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/shippingAddress/addShippingAddress",headers = {})
	public void addShippingAddress(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ShippingAddress vo = (ShippingAddress) getVo(req, ShippingAddress.class);
		wrint(res, userService.addShippingAddress(vo));
	}
	
	//地址编辑
	@RequestMapping(value="/shippingAddress/editShippingAddress",headers = {})
	public void editShippingAddress(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ShippingAddress vo = (ShippingAddress) getVo(req, ShippingAddress.class);
		wrint(res, userService.editShippingAddress(vo));
	}
	
	
}

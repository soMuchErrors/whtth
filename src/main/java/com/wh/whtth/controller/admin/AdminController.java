package com.wh.whtth.controller.admin;

import com.wh.whtth.controller.BaseController;
import com.wh.whtth.model.Shop;
import com.wh.whtth.model.User;
import com.wh.whtth.service.AdminService;
import com.wh.whtth.vo.ShopManageVo;
import com.wh.whtth.vo.ViewVo;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{
	
	@Autowired
	private AdminService adminService;

	/**
	 * 上传图片文件
	 * @param req
	 * @param res
	 * @param img
     * @return
     */
	@RequestMapping(value = "/upload/image",method = RequestMethod.POST)
	public @ResponseBody String uploadImg(HttpServletRequest req, HttpServletResponse res, @RequestBody MultipartFile img) throws Exception{
		String originalFilename = img.getOriginalFilename();
		String newFileName = "";
		if(img != null && !StringUtils.isEmpty(originalFilename)){
			newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
			File newFile = new File(IMG_PATH+newFileName);
			img.transferTo(newFile);

		}
		return newFileName;
	}
	
	//新增商家
	@RequestMapping(value="/shopManage/addShopManager",method = RequestMethod.POST,headers = {})
	public void addShopManager(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ShopManageVo vo = (ShopManageVo)getVo(req, ShopManageVo.class);
		wrint(res, adminService.addShopManager(vo));
	}
	
	//商家管理  有效商家列表
	@RequestMapping(value="/shopManage/listValidShops",method = RequestMethod.POST,headers = {})
	public @ResponseBody List<Shop> listValidShops(@RequestBody ViewVo vo,HttpServletRequest req,HttpServletResponse res) throws Exception{
//		ViewVo vo = (ViewVo)getVo(req, ViewVo.class);
//		wrint(res, adminService.listValidShops(vo));
		return adminService.listValidShops(vo);
	}
	
	//商家管理  无效商家列表
	@RequestMapping(value = "/shopManage/listInvalidShops", method = RequestMethod.POST, headers = {})
	public void listInvalidShops(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ViewVo vo = (ViewVo) getVo(req, ViewVo.class);
		wrint(res, adminService.listInvalidShops(vo));
	}
	
	//下架商家
	@RequestMapping(value = "/shopManage/makeShopInvalid",headers = {})
	public void makeShopInvalid(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		wrint(res, adminService.makeShopInvalid(req.getParameter("id")));
	}
	
	//回复商家
	@RequestMapping(value = "/shopManage/makeShopValid", headers = {})
	public void makeShopValid(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		wrint(res, adminService.makeShopValid(req.getParameter("id")));
	}
	
	//设置折扣
	@RequestMapping(value = "/shopManage/setRate", headers = {})
	public void setRate(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		wrint(res, adminService.setRate(req.getParameter("id"),req.getParameter("discount"),req.getParameter("pushmoney")));
	}
	
	
	//查看商品审核列表
	@RequestMapping(value="/productManage/checkPendingProducts",method = RequestMethod.POST,headers = {})
	public void checkPendingProducts(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ViewVo vo = (ViewVo)getVo(req, ViewVo.class);
		wrint(res, adminService.checkPendingProducts(vo));
	}
	
	//审核通过
	@RequestMapping(value="/productManage/approveAudit",headers = {})
	public void approveAudit(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, adminService.approveAudit(req.getParameter("id")));
	}
	
	//审核不通过
	@RequestMapping(value="/productManage/nonApproval",headers = {})
	public void nonApproval(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, adminService.nonApproval(req.getParameter("id")));
	}
	
	//查看商品详情
	@RequestMapping(value="/productManage/viewProduct",headers = {})
	public void viewProduct(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, adminService.viewProduct(req.getParameter("id")));
	}
	
	//新建会员
	@RequestMapping(value="/userManage/addUser",method = RequestMethod.POST,headers = {})
	public void addUser(HttpServletRequest req,HttpServletResponse res) throws Exception{
		User vo = (User)getVo(req, User.class);
		wrint(res, adminService.addUser(vo));
	}
	
	//下架会员
	@RequestMapping(value="/userManage/makeUserInvalid",headers = {})
	public void makeUserInvalid(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, adminService.makeUserInvalid(req.getParameter("id")));
	}
	
	//上架会员
	@RequestMapping(value="/userManage/makeUserValid",headers = {})
	public void makeUserValid(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, adminService.makeUserValid(req.getParameter("id")));
	}
	
	//充值
	@RequestMapping(value="/userManage/recharge",headers = {})
	public void recharge(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, adminService.recharge(req.getParameter("id"),req.getParameter("money")));
	}
	
	//个人信息
	@RequestMapping(value="/userManage/userinfo",headers = {})
	public void userinfo(HttpServletRequest req,HttpServletResponse res) throws Exception{
		wrint(res, adminService.userinfo(req.getParameter("id")));
	}
	
	//个人消费信息
	@RequestMapping(value="/userManage/shoppingInfo",method = RequestMethod.POST,headers = {})
	public void shoppingInfo(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ViewVo vo = (ViewVo)getVo(req, ViewVo.class);
		wrint(res, adminService.shoppingInfo(vo));
	}
}

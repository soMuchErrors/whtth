package com.wh.whtth.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wh.whtth.idao.ShopMapper;
import com.wh.whtth.idao.UserMapper;
import com.wh.whtth.vo.ShopManageVo;
import com.wh.whtth.vo.ViewVo;

@Transactional
@Component("shopManageService")
public class ShopManageService {

	@Resource(name = "shopDao")
	private ShopMapper shopDao;
	
	@Resource(name = "userDao")
	private UserMapper userDao;
	
	public List<ShopManageVo> view(Object viewVo) {
		ViewVo vo = (ViewVo) viewVo;
		if (vo.getId()!=null) {
			return shopDao.viewById(vo);
		} else {
			vo.setStart(vo.getPagesize() * (vo.getPage() - 1));
			return shopDao.view(vo);
		}
	}

	public String eidt(Object shopManageVo) {
		ShopManageVo vo = (ShopManageVo) shopManageVo;
		int shopstate =0;
		int userstate = 0;
		if(vo.getShop() != null && !StringUtils.isEmpty(vo.getShop().getId()))
			shopstate = shopDao.updateByPrimaryKeySelective(vo.getShop());
		if(vo.getUser()!=null && !StringUtils.isEmpty(vo.getUser().getId()))
			userstate = userDao.updateByPrimaryKeySelective(vo.getUser());
		if(shopstate==0 && userstate==0 )
			return "更新失败";
		else
			return "更新成功";
	}
}

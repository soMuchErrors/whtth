package com.wh.whtth.vo;

import java.util.List;

import com.wh.whtth.model.Shop;
import com.wh.whtth.model.User;


/**
 * 店铺信息
 * @author lmh
 *
 */
public class ShopManageVo{
	private Shop shop;
	private User user;
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}

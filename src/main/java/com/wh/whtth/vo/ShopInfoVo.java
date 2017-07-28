package com.wh.whtth.vo;


import java.util.List;

import com.wh.whtth.model.Shop;

public class ShopInfoVo {
	private Shop shop;
	
	private List<SortProductVo>  sortProducts;

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<SortProductVo> getSortProducts() {
		return sortProducts;
	}

	public void setSortProducts(List<SortProductVo> sortProducts) {
		this.sortProducts = sortProducts;
	}

}

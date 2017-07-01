package com.wh.whtth.vo;

import java.util.List;
import java.util.Map;

import com.wh.whtth.model.Product;
import com.wh.whtth.model.Shop;

public class ShopInfoVo {
	private Shop shop;
	
	private Map<String,List<Product>>  products;

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Map<String, List<Product>> getProducts() {
		return products;
	}

	public void setProducts(Map<String, List<Product>> products) {
		this.products = products;
	}

}

package com.wh.whtth.vo;

import java.util.List;
import java.util.Map;

public class SortProductVo {
	private SortVo sort;
	
	private List<Map<String,String>> products;

	public SortVo getSort() {
		return sort;
	}

	public void setSort(SortVo sort) {
		this.sort = sort;
	}

	public List<Map<String, String>> getProducts() {
		return products;
	}

	public void setProducts(List<Map<String, String>> products) {
		this.products = products;
	}
}

package com.wh.whtth.vo;

import java.util.List;
import java.util.Map;

public class OrderDetail {
	private Map<String,String> order;
	
	private List<Map<String,String>> goods;

	public Map<String, String> getOrder() {
		return order;
	}

	public void setOrder(Map<String, String> order) {
		this.order = order;
	}

	public List<Map<String, String>> getGoods() {
		return goods;
	}

	public void setGoods(List<Map<String, String>> goods) {
		this.goods = goods;
	}
}

package com.wh.whtth.model;

import java.math.BigDecimal;

public class Shop {
    private Long id;

    private String name;

    private Long userid;

    private String shopdesc;

    private String picture;

    private Double discount;

    private String paymentcode;

    private String paymentlink;

    private Double postage;

    private String telphone;

    private String address;

    private Integer state;

    private String trade;
    
    private String tradename;

    private Double pushmoney;

    private BigDecimal corperationmoney;
    	
    public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getShopdesc() {
        return shopdesc;
    }

    public void setShopdesc(String shopdesc) {
        this.shopdesc = shopdesc;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getPaymentcode() {
        return paymentcode;
    }

    public void setPaymentcode(String paymentcode) {
        this.paymentcode = paymentcode;
    }

    public String getPaymentlink() {
        return paymentlink;
    }

    public void setPaymentlink(String paymentlink) {
        this.paymentlink = paymentlink;
    }

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public Double getPushmoney() {
        return pushmoney;
    }

    public void setPushmoney(Double pushmoney) {
        this.pushmoney = pushmoney;
    }

    public BigDecimal getCorperationmoney() {
        return corperationmoney;
    }

    public void setCorperationmoney(BigDecimal corperationmoney) {
        this.corperationmoney = corperationmoney;
    }
}
package com.wh.whtth.model;

public class Order {
    private Long id;

    private Long userid;

    private Long shopid;

    private Long addressid;

    private Double amount;

    private Integer state;

    private Long postmanid;

    private String createtime;

    private String enddtime;
    
    //快递公司
    private String express;
    
    //快递单号
    private String trackingnum;
    
    //退款原因
    private String reason;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public Long getAddressid() {
        return addressid;
    }

    public void setAddressid(Long addressid) {
        this.addressid = addressid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getPostmanid() {
        return postmanid;
    }

    public void setPostmanid(Long postmanid) {
        this.postmanid = postmanid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getEnddtime() {
        return enddtime;
    }

    public void setEnddtime(String enddtime) {
        this.enddtime = enddtime;
    }

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getTrackingnum() {
		return trackingnum;
	}

	public void setTrackingnum(String trackingnum) {
		this.trackingnum = trackingnum;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
    
    
}
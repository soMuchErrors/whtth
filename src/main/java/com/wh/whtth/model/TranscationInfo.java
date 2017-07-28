package com.wh.whtth.model;

public class TranscationInfo {
    private Long id;

    private Long buyer;

    private Long seller;

    private Long timestamp;

    private Double amount;

    private Integer type;

    private String ymd;

    private String ym;

    private String year;

    private Long orderid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyer() {
        return buyer;
    }

    public void setBuyer(Long buyer) {
        this.buyer = buyer;
    }

    public Long getSeller() {
        return seller;
    }

    public void setSeller(Long seller) {
        this.seller = seller;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }
}
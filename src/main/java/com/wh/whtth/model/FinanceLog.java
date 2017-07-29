package com.wh.whtth.model;

public class FinanceLog {
    private Long id;

    private Long userid;

    private Integer cashiermode;

    private Double money;

    private Long timestamp;

    private String ymd;

    private String ym;

    private String year;

    private Long orderid;

    private String loginfo;

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

    public Integer getCashiermode() {
        return cashiermode;
    }

    public void setCashiermode(Integer cashiermode) {
        this.cashiermode = cashiermode;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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

    public String getLoginfo() {
        return loginfo;
    }

    public void setLoginfo(String loginfo) {
        this.loginfo = loginfo;
    }
}
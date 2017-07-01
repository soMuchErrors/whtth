package com.wh.whtth.model;

public class ProductSort {
    private Long id;

    private Long shopid;

    private Integer sortcode;

    private String sortname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public Integer getSortcode() {
        return sortcode;
    }

    public void setSortcode(Integer sortcode) {
        this.sortcode = sortcode;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }
}
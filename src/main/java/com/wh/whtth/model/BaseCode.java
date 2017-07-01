package com.wh.whtth.model;

public class BaseCode {
    private Long id;

    private Integer codetype;

    private String codenum;

    private String codename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodetype() {
        return codetype;
    }

    public void setCodetype(Integer codetype) {
        this.codetype = codetype;
    }

    public String getCodenum() {
        return codenum;
    }

    public void setCodenum(String codenum) {
        this.codenum = codenum;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }
}
package com.huayuan.oa.util.networkutil.entry;

import java.io.Serializable;

public class StatusBean implements Serializable {
    private String code;
    private String msg;

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

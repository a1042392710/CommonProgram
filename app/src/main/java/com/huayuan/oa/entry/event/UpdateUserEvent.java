package com.huayuan.oa.entry.event;

import java.io.Serializable;

public class UpdateUserEvent implements Serializable {

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message ;



}

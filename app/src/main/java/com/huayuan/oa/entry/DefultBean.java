package com.huayuan.oa.entry;

import java.io.Serializable;

/**
 * @author chenhao 2018/9/30
 * @function 默认的，无返回数据的类
 */
public class DefultBean implements Serializable{

    public String getFace_img() {
        return face_img == null ? "" : face_img;
    }

    public void setFace_img(String face_img) {
        this.face_img = face_img;
    }

    private  String face_img;
}

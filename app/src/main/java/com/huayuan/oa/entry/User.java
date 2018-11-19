package com.huayuan.oa.entry;

import java.io.Serializable;

/**
 * @author chenhao 2018/9/30
 * @function
 */
public class User implements Serializable {
    private String name;
    private String dname;
    private String id;
    private String level;
    private String user;
    private String face;
    private int sex;
    private String mobile;
    private String cname;
    private String birthday;
    private String pname;

    public String getLevel() {
        return level == null ? "" : level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCname() {
        return cname == null ? "" : cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPname() {
        return pname == null ? "" : pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDname() {
        return dname == null ? "" : dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user == null ? "" : user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFace() {
        return face == null ? "" : face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

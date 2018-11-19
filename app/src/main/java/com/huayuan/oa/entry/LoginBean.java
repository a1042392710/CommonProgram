package com.huayuan.oa.entry;

import java.io.Serializable;

/**
 * @author chenhao 2018/9/29
 * @function 登录
 */
public class LoginBean implements Serializable {

    /**
     * login_credentials : 1d2estVSVPJw3qXYHX0s8fAmWvct*hua*ZdbhrNbi4la
     * // User 里的内容
     * "dname":"开发部",        //部门名称
     * "id":1,        //用户id
     * "user":"admin",        //用户名
     * "name":"管理员",        //姓名
     * "face":"/aaaa/aaa/aa",        //头像地址
     * "sex":"男",        //性别
     * "mobile":"0592-1234569",//手机号
     * "pname":"管理员"        //岗位名
     *  birthday   生日
     */

    private String login_credentials;
    private User user;

    public String getLogin_credentials() {
        return login_credentials == null ? "" : login_credentials;
    }

    public void setLogin_credentials(String login_credentials) {
        this.login_credentials = login_credentials;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}

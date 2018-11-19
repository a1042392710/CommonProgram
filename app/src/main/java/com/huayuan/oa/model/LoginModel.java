package com.huayuan.oa.model;

import com.huayuan.oa.api.Api;
import com.huayuan.oa.base.BaseModel;
import com.huayuan.oa.entry.LoginBean;
import com.huayuan.oa.util.networkutil.RxSchedulerHepler;

import io.reactivex.Flowable;


/**
 * @author chenhao 2018/9/20
 * @function 登录
 */
public class LoginModel extends BaseModel {

    /**
     * 登录
     * @param requestData
     */

    public Flowable<LoginBean> login(String requestData) {

        return  Api.getInstance()
                .service
                .login(requestData)
                .compose(RxSchedulerHepler.<LoginBean>handleMyResult());

    }


}

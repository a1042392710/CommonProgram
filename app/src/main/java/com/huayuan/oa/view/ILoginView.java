package com.huayuan.oa.view;

import com.huayuan.oa.base.IBaseView;
import com.huayuan.oa.entry.LoginBean;

/**
 * @author chenhao 2018/9/25
 * @function 登陆
 */
public interface ILoginView extends IBaseView {

    void isSuccess(LoginBean data);
    void isFail(String msg);

}

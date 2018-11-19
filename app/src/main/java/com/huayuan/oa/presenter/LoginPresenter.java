package com.huayuan.oa.presenter;

import com.huayuan.oa.base.BasePresenter;
import com.huayuan.oa.entry.LoginBean;
import com.huayuan.oa.model.LoginModel;
import com.huayuan.oa.util.networkutil.CommonSubscriber;
import com.huayuan.oa.view.ILoginView;

/**
 * @author chenhao 2018/9/29
 * @function 登录
 */
public class LoginPresenter extends BasePresenter<LoginModel, ILoginView> {


    public LoginPresenter(ILoginView view) {
        initPresenter(view);
    }


    /**
     * 登录
     *
     * @param pack_no
     */
    public void login(String pack_no) {

        addSubscribe(mModel.login(pack_no)
                .subscribeWith(new CommonSubscriber<LoginBean>() {

                    @Override
                    protected void startLoading() {
                        mView.showLoading("登录中，请稍后...");
                    }

                    @Override
                    protected void onSuccess(LoginBean response) {
                        mView.stopLoading();
                        mView.isSuccess(response);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        mView.isFail(errorMsg);
                        mView.stopLoading();
                    }
                }));

    }


    @Override
    protected LoginModel createModel() {
        return new LoginModel();
    }

}

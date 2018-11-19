package com.huayuan.oa.ui.activity;

import android.os.Bundle;

import com.huayuan.oa.R;
import com.huayuan.oa.base.BaseActivity;
import com.huayuan.oa.base.BasePresenter;

/**
 * @author chenhao 2018/11/19
 * @function
 */
public class MainActivity extends BaseActivity {
    @Override
    protected int getResViewId() {
        return R.layout.act_main;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void stopLoading() {

    }
}

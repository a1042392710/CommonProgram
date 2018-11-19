package com.huayuan.oa.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huayuan.oa.R;
import com.huayuan.oa.api.Constant;
import com.huayuan.oa.util.LoadingDialogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Fragment基础类
 * Created by chenhao on 2018/8/28.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {
    //得到类名
    protected final String TAG = this.getClass().getName();
    protected P mPresenter;
    /**
     * 根视图
     */
    protected View mRootView;
    /**
     * 全APP 进程
     */
    protected BaseApplication mApp;
    /**
     * 上下文对象
     */
    protected Context mContext;
    protected ProgressDialog mProgressDialog; //弹窗
    /**
     * 吐司
     */
    protected Toast mToast;
    protected boolean isInit;//是否初始化
    /**
     * 注解
     */
    private Unbinder unbinder;
    /**
     * 弹窗
     */
    private Dialog mloadingDialog;

    /**
     * fragment不可见
     */
    private boolean isViewVisiable = false;

    /**
     * 是否准备
     */
    private boolean isPrepared = false;

    /**
     *  是否加载数据
     */
    private boolean isDataAdd = false;

    //********************************************* 生命周期

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attach(context);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        attach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getViewResId(), container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        isPrepared = true;
        if (isViewVisiable && !isDataAdd) {
            load();
        }
        return mRootView;
    }
    /**
     * 初始化数据
     */
    private void load() {
        mPresenter = getPresenter();
        initData();
        isDataAdd = true;

    }

    /**
     *  只在可见时加载数据
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisiable = isVisibleToUser;
        if (isViewVisiable && isPrepared && !isDataAdd) {
            load();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (unbinder != null) {
            unbinder.unbind(); //注解框架解绑
        }
        //取消 view 的绑定
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        isPrepared = false;
        isViewVisiable = false;
        isDataAdd = false;
    }

    private void attach(Context context) {
        mContext = context;
        mApp = (BaseApplication) context.getApplicationContext();

    }


    //********************************************* 实用方法
    DynamicBox box;
    protected DynamicBox createDynamicBox(View view){
        if (box==null) {
            box = new DynamicBox(getActivity(), view);
            View customView = getLayoutInflater().inflate(R.layout.include_no_data, null, false);
            box.addCustomView(customView, "noNewWork");
        }
        box.showCustomView("noNewWork");
        return box;
    }


    protected void hideBox() {
        if (box != null) {
            box.hideAll();
        }
    }


    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showToastLong(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        }
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        startProgressDialog(Constant.TOAST_LODING);
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        mloadingDialog = LoadingDialogUtil.showDialogForLoading(getActivity(), msg, true);
    }

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialogUtil.cancelDialogForLoading(mloadingDialog);
    }

    /**
     * 关闭刷新
     */
    protected  void closeRefresh(SmartRefreshLayout view){
        view.finishRefresh(300);
        view.finishLoadMore(300);
    }


    //********************************************* 子类实现

    protected abstract P getPresenter();

    //初始化
    protected abstract void initData();

    //布局ID
    protected abstract int getViewResId();

}
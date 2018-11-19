package com.huayuan.oa.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huayuan.oa.R;
import com.huayuan.oa.api.Constant;
import com.huayuan.oa.broadcast.NetWorkChangeBroadcastReceiver;
import com.huayuan.oa.util.ActivityManager;
import com.huayuan.oa.util.LoadingDialogUtil;
import com.huayuan.oa.util.NetWorkUtils;
import com.huayuan.oa.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by chenhao on 2018/8/28.
 */

public abstract class BaseActivity<P extends BasePresenter> extends SwipeBackActivity implements
        IBaseView {
    /**
     * Presenter
     */
    protected P mPresenter;
    /**
     * 上下文对象
     */
    protected Context mContext;
    /**
     * 判断网络是否正常
     */
    protected boolean IS_NETWORK_ERROR_SHOWING = false;
    /**
     * 提示
     */
    protected Toast mTasot;
    /**
     * 注解框架绑定管理
     */
    private Unbinder unbinder;
    /**
     * 网络变化显示的view
     */
    private View tipsView = null;
    /**
     * 根布局
     */
    private FrameLayout rootView = null;
    /**
     * 通用加载dialog
     */
    private Dialog mloadingDialog;
    /**
     * 右滑退出
     */
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置标题栏
        StatusBarUtil.setColorNoTranslucent(this, Color.TRANSPARENT);
        mContext = this;
        setContentView(getResViewId());
        //注解框架的实例化
        unbinder = ButterKnife.bind(this);
        //获取presenter
        mPresenter = getPresenter();
        //注册EventBus
        EventBus.getDefault().register(this);
        //添加activity
        ActivityManager.getAppManager().addActivity(this);
        //设置右滑退出
        initSwipeBack();
        //初始化数据
        initData(savedInstanceState);
    }


    //*************************************   activity 相关配置/跳转/关闭

    /**
     * 初始化右滑退出
     */
    private void initSwipeBack() {
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);

        mSwipeBackLayout = getSwipeBackLayout();

        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        // mSwipeBackLayout.setEdgeSize(200);

    }
    /**
     * 关闭右滑退出
     */

    protected void closeSwipeBack() {
        setSwipeBackEnable(false);
    }

    /**
     * 跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * 含有Bundle跳转界面回调
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    //***************************************************     实用方法

    /**
     * 关闭刷新
     */
    protected  void closeRefresh(SmartRefreshLayout view){
        view.finishRefresh(300);
        view.finishLoadMore(300);
    }

    /**
     * 收回软键盘
     */

    protected  void  disMissSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    //提示
    public void showToast(String msg) {
        if (mTasot == null) {
            mTasot = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        mTasot.setText(msg);
        mTasot.setDuration(Toast.LENGTH_SHORT);
        mTasot.show();
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
        mloadingDialog = LoadingDialogUtil.showDialogForLoading(this, msg, true);
    }

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialogUtil.cancelDialogForLoading(mloadingDialog);
    }


    //***************************************  网络状态发生变化

    /**
     * 初始化网络变动view
     */
    public void initTipsView() {
        View decorView = getWindow().getDecorView();
        rootView = decorView.findViewById(android.R.id.content);
        tipsView = getLayoutInflater().inflate(R.layout.view_top_not_network, null);
        TextView tv_not_network = tipsView.findViewById(R.id.tv_not_network);
        tv_not_network.setOnClickListener(v ->
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS)));

    }

    /**
     * 收到网络改变通知
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NetWorkChangeBroadcastReceiver event) {
        Log.d("NetWorkChange", getLocalClassName() + "network changed");
        if (NetWorkUtils.isNetConnected(mContext)) {
            removeTipsView();
        } else {
            addTipsView();
        }
    }

    /**
     * 移除网络变动view
     */
    public void removeTipsView() {
        if (!IS_NETWORK_ERROR_SHOWING)
            return;
        if (tipsView == null)
            return;

        TextView tipText = tipsView.findViewById(R.id.tv_not_network);
        tipText.setText(getResources().getString(R.string.have_net));
        LinearLayout rl_check = tipsView.findViewById(R.id.rl_check);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.snackbar_top_hide);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rootView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rootView.removeView(tipsView);
                        IS_NETWORK_ERROR_SHOWING = false;
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        rl_check.startAnimation(anim);

    }

    /**
     * 添加网络变动view
     */
    public void addTipsView() {
        if (IS_NETWORK_ERROR_SHOWING)
            return;

        if (tipsView == null)
            initTipsView();
        TextView tipText = tipsView.findViewById(R.id.tv_not_network);
        tipText.setText(getResources().getString(R.string.no_net));

        rootView.addView(tipsView);
        IS_NETWORK_ERROR_SHOWING = true;
    }

    //*******************************************  生命周期和子类实现

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
        //解除注解
        if (unbinder != null) {
            unbinder.unbind();
        }
        //取消 view 的绑定
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        ActivityManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (tipsView == null) {
            initTipsView();
        }
        onEventMainThread(null);
    }


    //布局文件
    protected abstract int getResViewId();

    //获得presenter
    protected abstract P getPresenter();
    //初始化
    protected abstract void initData(Bundle savedInstanceState);

   private DynamicBox box;

    protected DynamicBox createDynamicBox(View view){
        if (box==null){
            box = new DynamicBox(this,view);
            View customView = getLayoutInflater().inflate(R.layout.include_no_data, null, false);
            box.addCustomView(customView,"noNewWork");
        }
        box.showCustomView("noNewWork");
        return box;
    }

    protected void hideDynamicBox(){
        if (box!=null){
            box.hideAll();
        }
    }
}

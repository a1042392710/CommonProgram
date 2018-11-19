package com.huayuan.oa.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huayuan.oa.R;
import com.huayuan.oa.base.BaseActivity;
import com.huayuan.oa.base.BasePresenter;
import com.huayuan.oa.util.networkutil.entry.UserLoginBiz;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author chenhao 2018/9/26
 * @function 欢迎页 顺便获取权限
 */
@RuntimePermissions
public class SplashActivity extends BaseActivity {
    @BindView(R.id.img_splash)
    ImageView imgSplash;

    @Override
    protected int getResViewId() {
        return R.layout.act_splash;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //关闭右滑退出
        closeSwipeBack();
        //设置背景图
        Glide.with(mContext).load(R.mipmap.ic_splash).into(imgSplash);
        //获取权限
        SplashActivityPermissionsDispatcher.getPermissionsWithCheck(this);
    }

    //*****************************************************************  请求权限

    //获取多个权限  必须要的方法  (存储，电话，定位)
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION})
    public void getPermissions() {
        //获取所有权限成功
        try {
            //停留1.5秒后进入主页，或者登录页面
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //已登录
        if (UserLoginBiz.getInstance(mContext).detectUserLoginStatus()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            //未登录
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }


    //给用户解释要请求什么权限，为什么需要此权限 ( 多个 )  非必须的方法
    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION})
    void showRationale(final PermissionRequest request) {
        showToastDialog(request);
    }

    private void showToastDialog(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_title)
                .setMessage(R.string.permission_message)
                .setCancelable(false)
                .setPositiveButton(R.string.permission_button_application, (dialog, which) -> {
                    request.proceed();//继续执行请求
                }).setNegativeButton(R.string.cancle, (dialog, which) -> {
            request.cancel();//取消执行请求
            finish();
        }).show();

    }


    //用户拒绝后回调的方法  非必须的方法
    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION})
    public void multiDenied() {
        SplashActivityPermissionsDispatcher.getPermissionsWithCheck(this);
    }

    //用户选择的不再询问后回调  非必须的方法
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION})
    public void multiNeverAsk() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_title)
                .setMessage(R.string.permission_message_setting)
                .setCancelable(false)
                .setPositiveButton(R.string.permission_button_setting, (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri1 = Uri.parse("package:" + getPackageName());
                    intent.setData(uri1);
                    startActivityForResult(intent, 20);
                }).setNegativeButton(R.string.cancle, (dialog, which) -> finish()).show();


    }


    // 必须要复写的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void stopLoading() {

    }



}

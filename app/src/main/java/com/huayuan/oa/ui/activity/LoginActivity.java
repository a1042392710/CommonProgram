package com.huayuan.oa.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huayuan.oa.R;
import com.huayuan.oa.api.ConstantApi;
import com.huayuan.oa.base.BaseActivity;
import com.huayuan.oa.base.BaseApplication;
import com.huayuan.oa.entry.LoginBean;
import com.huayuan.oa.presenter.LoginPresenter;
import com.huayuan.oa.util.SpUtil;
import com.huayuan.oa.util.StringUtils;
import com.huayuan.oa.util.networkutil.PacketUtil;
import com.huayuan.oa.util.networkutil.entry.UserLoginBiz;
import com.huayuan.oa.view.ILoginView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * @author chenhao 2018/9/25
 * @function 登录
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.img_launcher)
    ImageView imgLauncher;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.view_line)
    View viewLine;
    //退出时的时间
    private long mExitTime;

    @Override
    protected int getResViewId() {
        return R.layout.act_login;
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //显示上一次登录的用户名
        etPhone.setText(SpUtil.init(mContext).readString("account"));
        //关闭右滑退出
        closeSwipeBack();
        //禁用登录按钮，必须输入密码后才可点击
        tvLogin.setEnabled(false);
        //监听密码输入情况
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtils.isEmpty(etPhone.getText().toString())) {
                    //必须输入密码后才可点击登录
                    if (s.length() > 0) {
                        tvLogin.setEnabled(true);
                        tvLogin.setBackgroundResource(R.drawable.shape_login_select_bg);
                    } else {
                        tvLogin.setEnabled(false);
                        tvLogin.setBackgroundResource(R.drawable.shape_login_no_select_bg);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void isSuccess(LoginBean data) {
        //恢复推送服务
        JPushInterface.resumePush(getApplicationContext());
        //存储用户Id
        SpUtil.init(BaseApplication.getAppContext()).commit(ConstantApi.LOGIN_ID, data.getLogin_credentials());
        //存储用户信息
        UserLoginBiz.getInstance(BaseApplication.getAppContext()).loginSuccess(data);
        //todo 跳转首页
        startActivity(MainActivity.class, null);
        finish();
    }

    @Override
    public void isFail(String msg) {
        showToast(msg);
    }

    @Override
    public void showLoading(String msg) {
        startProgressDialog(msg);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }


    @OnClick({R.id.tv_forget_password, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
//                startActivity(ForgetPasswordActivity.class, null);
                break;
            case R.id.tv_login:
                if (!StringUtils.isMobileNO(etPhone.getText().toString())) {
                    showToast("请输入正确的手机号码");
                    return;
                }

                if (StringUtils.isEmpty(etPassword.getText().toString())) {
                    showToast("请输入密码");
                    return;
                }
                disMissSoftKeyboard();//收起软键盘
                //存储账户名
                SpUtil.init(mContext).commit("account",etPhone.getText().toString().trim());
                //登录
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("username", etPhone.getText().toString().trim());
                hashMap.put("password", StringUtils.MD5(etPassword.getText().toString().trim()));
                mPresenter.login(PacketUtil.getRequestPacket(this, ConstantApi.PACK_LOGIN, hashMap));
                break;
        }
    }

    //监听返回按钮
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出app
    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            showToast("再按一次退出华源办公");
            mExitTime = System.currentTimeMillis();
        } else {
            //用户退出处理
            finish();
            System.exit(0);
        }
    }

}

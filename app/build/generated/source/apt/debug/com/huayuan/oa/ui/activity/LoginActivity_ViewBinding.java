// Generated code from Butter Knife. Do not modify!
package com.huayuan.oa.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.huayuan.oa.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding<T extends LoginActivity> implements Unbinder {
  protected T target;

  private View view2131755192;

  private View view2131755193;

  @UiThread
  public LoginActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'etPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_forget_password, "field 'tvForgetPassword' and method 'onViewClicked'");
    target.tvForgetPassword = Utils.castView(view, R.id.tv_forget_password, "field 'tvForgetPassword'", TextView.class);
    view2131755192 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_login, "field 'tvLogin' and method 'onViewClicked'");
    target.tvLogin = Utils.castView(view, R.id.tv_login, "field 'tvLogin'", TextView.class);
    view2131755193 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.imgLauncher = Utils.findRequiredViewAsType(source, R.id.img_launcher, "field 'imgLauncher'", ImageView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.viewLine = Utils.findRequiredView(source, R.id.view_line, "field 'viewLine'");
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.etPhone = null;
    target.etPassword = null;
    target.tvForgetPassword = null;
    target.tvLogin = null;
    target.imgLauncher = null;
    target.tvTitle = null;
    target.viewLine = null;

    view2131755192.setOnClickListener(null);
    view2131755192 = null;
    view2131755193.setOnClickListener(null);
    view2131755193 = null;

    this.target = null;
  }
}

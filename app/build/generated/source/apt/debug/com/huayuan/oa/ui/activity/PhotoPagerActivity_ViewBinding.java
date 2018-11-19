// Generated code from Butter Knife. Do not modify!
package com.huayuan.oa.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.huayuan.oa.R;
import com.huayuan.oa.util.ViewPagerFixed;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PhotoPagerActivity_ViewBinding<T extends PhotoPagerActivity> implements Unbinder {
  protected T target;

  private View view2131755194;

  @UiThread
  public PhotoPagerActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.ll_left, "field 'llLeft' and method 'onViewClicked'");
    target.llLeft = Utils.castView(view, R.id.ll_left, "field 'llLeft'", LinearLayout.class);
    view2131755194 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.vpPhotos = Utils.findRequiredViewAsType(source, R.id.vp_photos, "field 'vpPhotos'", ViewPagerFixed.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.llLeft = null;
    target.tvTitle = null;
    target.vpPhotos = null;

    view2131755194.setOnClickListener(null);
    view2131755194 = null;

    this.target = null;
  }
}

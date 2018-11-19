package com.huayuan.oa.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.huayuan.oa.R;
import com.huayuan.oa.base.BaseActivity;
import com.huayuan.oa.base.BasePresenter;
import com.huayuan.oa.util.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图片预览
 * @author chenhao 2018/9/11
 */
public class PhotoPagerActivity<T> extends BaseActivity {
    @BindView(R.id.ll_left)
    LinearLayout llLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.vp_photos)
    ViewPagerFixed vpPhotos;
    /**
     * 数据源
     */
    private List<Object> mList;
    /**
     * 适配器
     */
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected int getResViewId() {
        return R.layout.act_photo_pager;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mList = (List<Object>) getIntent().getSerializableExtra("photos");

        if (null != mList && mList.size() > 0) {

            if (mList.size()==1){
                tvTitle.setText("预览");
            }else{
                tvTitle.setText("预览1/" + mList.size());
            }
        } else {
            mList = new ArrayList<>();
        }
         //初始化数据
        myPagerAdapter = new MyPagerAdapter();
        vpPhotos.setAdapter(myPagerAdapter);
        vpPhotos.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText("预览" + (position + 1) + "/" + mList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showLoading(String msg) {
    }

    @Override
    public void stopLoading() {
    }


    @OnClick(R.id.ll_left)
    public void onViewClicked() {
        finish();
    }


    //动态加载图片
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ////保持宽高比
            photoView.setMinimumHeight(LinearLayout.LayoutParams.MATCH_PARENT);
            photoView.setMinimumWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            photoView.setAdjustViewBounds(true);
            Glide.with(mContext).load(mList.get(position)).into(photoView);
            container.addView(photoView);
            return photoView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}

package com.huayuan.oa.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.huayuan.oa.R;
import com.huayuan.oa.util.LogUtil;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ch on 2018/8/30.
 */
public class BaseApplication extends Application {

    private static  BaseApplication mContext;


    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.notice_line, android.R.color.white);//全局设置主题颜色
            return new BezierCircleHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //测试环境允许显示日志
        LogUtil.isShowing = true;
        //bugly 参数3  调试开关 /测试时true 发布时false
        Bugly.init(getApplicationContext(), "3013f43ad7", false);
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //内存泄漏检测
        LeakCanary.install(this);

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // 安装tinker
        Beta.installTinker();
    }



    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }



}
package com.huayuan.oa.base;

/**
 * Created by chenhao on 2018/8/30.
 */
//基础接口
public interface IBaseView {
    //显示进度
    void showLoading( String msg);

    //隐藏进度条
    void stopLoading();
}

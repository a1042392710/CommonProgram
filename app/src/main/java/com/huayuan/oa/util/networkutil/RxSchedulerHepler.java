package com.huayuan.oa.util.networkutil;


import com.huayuan.oa.api.ConstantApi;
import com.huayuan.oa.entry.ResponseData;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chenhao 2018/8/30
 * @function 线程转换辅助类
 */
public class RxSchedulerHepler {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<ResponseData<T>, T> handleMyResult() {   //compose判断结果
        return httpResponseFlowable -> httpResponseFlowable.flatMap((Function<ResponseData<T>, Flowable<T>>) response -> {
            if (ConstantApi.SUCCESS.equals(response.getStatus().getCode())||ConstantApi.NETWORK_NODATA.equals(response.getStatus().getCode())) {
                return createData(response.getData());
            } else {
                return Flowable.error(new Exception(
                         ( response.getStatus().getMsg())
                ));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 存储数据
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
    }


}

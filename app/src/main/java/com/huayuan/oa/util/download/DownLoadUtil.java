package com.huayuan.oa.util.download;


import com.huayuan.oa.api.ApiService;

import org.reactivestreams.Publisher;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ch on 2017/7/3.
 * 作用：下载工具类
 */

public class DownLoadUtil {
    public static Observable<DownloadStatus> download(String url, final String filePath, final String fileName) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        return apiService.download(url)
                .flatMap(new Function<ResponseBody, Publisher<DownloadStatus>>() {
                    @Override
                    public Publisher<DownloadStatus> apply(@NonNull final ResponseBody responseBody) throws Exception {
                        return Flowable.create(new FlowableOnSubscribe<DownloadStatus>() {
                            @Override
                            public void subscribe(FlowableEmitter<DownloadStatus> e) throws Exception {

                                File saveFile = new File(filePath, fileName);

                                InputStream inputStream = null;
                                OutputStream outputStream = null;
                                try {
                                    try {
                                        int readLen;
                                        int downloadSize = 0;
                                        byte[] buffer = new byte[1024];

                                        DownloadStatus status = new DownloadStatus();
                                        inputStream = responseBody.byteStream();
                                        outputStream = new FileOutputStream(saveFile);

                                        long contentLength = responseBody.contentLength();

                                        status.setTotalSize(contentLength);

                                        while ((readLen = inputStream.read(buffer)) != -1 && !e.isCancelled()) {
                                            outputStream.write(buffer, 0, readLen);
                                            downloadSize += readLen;
                                            status.setDownloadSize(downloadSize);
                                            e.onNext(status);
                                        }

                                        outputStream.flush(); // This is important!!!
                                        e.onComplete();
                                    } finally {
                                        closeQuietly(inputStream);
                                        closeQuietly(outputStream);
                                        closeQuietly(responseBody);
                                    }
                                } catch (Exception exception) {

                                }
                            }
                        }, BackpressureStrategy.LATEST);
                    }
                })
                .toObservable()
                .debounce(200, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }
}

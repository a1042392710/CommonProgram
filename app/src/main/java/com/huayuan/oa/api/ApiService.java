package com.huayuan.oa.api;

import com.huayuan.oa.entry.LoginBean;
import com.huayuan.oa.entry.ResponseData;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author chenhao 2018/8/30
 * @function  所有接口
 */
public interface ApiService  {

    // 域名 测试服务器
//    String BASE_HOST = "http://192.168.1.221/hyoa/public/index.php/";
    String BASE_HOST = " http://oa.hyuansc.com/index.php/";

    String INDEX = "app/api/processing";
    //报文参数
    String PACK_NO = "requestData";

    //********************************  用户

    //登录
    @FormUrlEncoded
    @POST(INDEX)
    Flowable<ResponseData<LoginBean>> login(
            @Field(PACK_NO) String pack_no
    );

    //虚拟下载接口
    @GET
    @Streaming
    Flowable<ResponseBody> download(@Url String url);

}

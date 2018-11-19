package com.huayuan.oa.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.huayuan.oa.entry.DefultBean;
import com.huayuan.oa.ui.activity.LoginActivity;
import com.huayuan.oa.util.LogUtil;
import com.huayuan.oa.util.networkutil.entry.UserLoginBiz;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "陈大帅的推送";
    /**
     * 通知类型
     */
    public static final String NOTICE_TYPE = "type";
    /**
     * 查询的Id
     */
    public static final String VALUE_ID = "id";
    /**
     * 通知的主键Id
     */
    public static final String N_ID = "nid";
    /**
     * 查询的Id
     */
    public static final String NOTICE_APPLICATION_TYPE = "view_type";//1 申请人   2 审批人  3 抄送人

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            Bundle bundle = intent.getExtras();
            LogUtil.d(TAG, "[MyReceiver]  " + intent.getAction() + ", extras: " + printBundle(bundle));
            //通知类型
            String type= "";
            //查询Id
            String id = "";
            //通知的主键 nId
            String nid = "";
            //申请单状态
            String view_type = "";
            //循环取出消息里面的数据
            JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            type = json.getString(NOTICE_TYPE);
            id = json.getString(VALUE_ID);
            view_type = json.getString(NOTICE_APPLICATION_TYPE);
            try {
                nid = json.getString(N_ID);
            }catch (Exception e){
                nid="";
            }


            LogUtil.e("通知类型", type+"id："+id);

            //接收到推送下来的自定义消息
            if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                LogUtil.e(TAG, "推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

                //接收到推送下来的通知
            }else if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                LogUtil.e("MyReceiver推送Id", regId);


            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                LogUtil.e(TAG, "推送下来的通知");
                //发送通知，提醒刷新
                EventBus.getDefault().post(new DefultBean());

                // 用户点击打开了通知
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

                //已登录
                if (UserLoginBiz.getInstance(context).detectUserLoginStatus()){
                    printBundle(bundle);
                }else{
                    //未登录
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            } else {
                LogUtil.e(TAG, "Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtil.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

}

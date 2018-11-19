package com.huayuan.oa.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;


/**
 * 检查网络情况
 */
public class NetWorkChangeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (!TextUtils.equals(action, ConnectivityManager.CONNECTIVITY_ACTION)) {
            return;
        }

        Log.d("NetWorkChange", "net work changed");

        // 发送eventbus
        EventBus.getDefault().post(new NetWorkChangeBroadcastReceiver());
    }
}
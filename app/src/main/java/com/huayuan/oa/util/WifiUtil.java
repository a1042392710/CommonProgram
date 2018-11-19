package com.huayuan.oa.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.util.List;

/**
 * @author chenhao 2018/10/9
 * @function 获取Wifi的信息
 */
public class WifiUtil {


    /**
     * 判断是否为工作 WIFI
     * @param context
     * @param wifiList 工作WIFI的Mac地址
     * @return 是/否
     */
    public static boolean isWorkWifi(Context context, List<String> wifiList) {
        String nowMacWifi = getConnectedWifiMacAddress(context);
        if (StringUtils.isEmpty(nowMacWifi)){
            nowMacWifi = "1111";
        }
        for (String s : wifiList) {
            //判断的时候不取最后三位
            if (s.substring(0, s.length() - 3).equals(nowMacWifi.substring(0, nowMacWifi.length() - 3))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取手机的Mac地址，在Wifi未开启或者未连接的情况下也能获取手机Mac地址
     */
    public static String getPhoneMacAddress(Context context) {
        String macAddress = null;
        WifiInfo wifiInfo = getWifiInfo(context);
        if (wifiInfo != null) {
            macAddress = wifiInfo.getMacAddress();
        }
        return macAddress;
    }

    /**
     * 获取SSID
     * @param activity 上下文
     * @return  WIFI 的SSID
     */
    public static String getWIFISSID(Activity activity) {
        String ssid="unknown id";

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O||Build.VERSION.SDK_INT==Build.VERSION_CODES.P) {

            WifiManager mWifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            assert mWifiManager != null;
            WifiInfo info = mWifiManager.getConnectionInfo();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return info.getSSID();
            } else {
                return info.getSSID().replace("\"", "");
            }
        } else if (Build.VERSION.SDK_INT==Build.VERSION_CODES.O_MR1){

            ConnectivityManager connManager = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            assert connManager != null;
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo.isConnected()) {
                if (networkInfo.getExtraInfo()!=null){
                    return networkInfo.getExtraInfo().replace("\"","");
                }
            }
        }
        return ssid;
    }
    /**
     * 获取WifiInfo
     */
    public static WifiInfo getWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        if (null != wifiManager) {
            info = wifiManager.getConnectionInfo();
        }
        return info;
    }

    /**
     * 获取手机的Ip地址
     */
    public static String getIpAddress(Context context) {
        String IpAddress = null;
        WifiInfo wifiInfo = getWifiInfo(context);
        if (wifiInfo != null) {
            IpAddress = intToIpAddress(wifiInfo.getIpAddress());
        }
        return IpAddress;
    }

    public static String intToIpAddress(long ipInt) {
        StringBuffer sb = new StringBuffer();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    public static long ipAddressToint(String ip) {
        String[] items = ip.split("\\.");
        return Long.valueOf(items[0]) << 24
                | Long.valueOf(items[1]) << 16
                | Long.valueOf(items[2]) << 8
                | Long.valueOf(items[3]);
    }

    /**
     * 获取当前可连接Wifi列表
     */
    public static List<?> getAvailableNetworks(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> wifiList = null;
        if (wifiManager != null) {
            wifiList = wifiManager.getScanResults();
        }
        return wifiList;
    }

    /**
     * 获取已连接的Wifi路由器的Mac地址
     */
    public static String getConnectedWifiMacAddress(Context context) {
        String connectedWifiMacAddress = null;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        List<ScanResult> wifiList;
//
//        if (wifiManager != null) {
//            wifiList = wifiManager.getScanResults();
//            WifiInfo info = wifiManager.getConnectionInfo();
//            if (wifiList != null && info != null) {
//                for (int i = 0; i < wifiList.size(); i++) {
//                    ScanResult result = wifiList.get(i);
//                    if (info.getBSSID().equals(result.BSSID)) {
//                        connectedWifiMacAddress = result.BSSID;
//                    }
//                }
//            }
//        }
        if (wifiManager != null) {
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                return  connectedWifiMacAddress = info.getBSSID();
            }
        }
        return connectedWifiMacAddress;
    }

}

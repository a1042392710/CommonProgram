package com.huayuan.oa.api;

/**
 * @author chenhao 2018/9/4
 * @function app 常量
 */
public class Constant {
    /**
     * 图片选择器 文件过滤
     */
    public static final String MATISSE_FILE_PATH = "com.huayuan.oa.fileprovider";
    /**
     * 定义相册请求码常量
     */
    public static final int REQUEST_CODE_CHOOSE = 23;

    /**
     * 传输实体类
     */
    public static final String CONSTANT_KEY_OBJECT = "bean";


    // ********************************** 通知类型

    //打卡
    public static final int NOTICE_PUNCH = 11;
    //请假
    public static final int NOTICE_LEAVE = 12;
    //外出
    public static final int NOTICE_GO_OUT = 13;
    //加班
    public static final int NOTICE_OVERTIME = 14;
    //合同申请
    public static final int NOTICE_CONTRACT = 15;
    //出差
    public static final int NOTICE_TRAVEL= 16;

    //转正
    public static final int NOTICE_TURN_POSITIVE = 21;
    //招聘
    public static final int NOTICE_RECRUITMENT = 22;

    //报销
    public static final int NOTICE_REIMBURSE= 31;
    //备用金申请
    public static final int NOTICE_RESERVE_FUND = 32;
    //付款申请
    public static final int NOTICE_PAY = 33;
    //差旅费
    public static final int NOTICE_TRAVEL_EXPENSES= 34;

    //抄送给我的周报
    public static final int NOTICE_CC_WEEKLY= 41;

    //采购
    public static final int NOTICE_PURCHASE = 91;
    //用车申请
    public static final int NOTICE_APPLY_CAR = 92;
    //物品领用
    public static final int NOTICE_ITEM_USE = 93;
    //用章
    public static final int NOTICE_USE_CHAPTER = 94;
    //公告
    public static final int NOTICE_ANNOUNCEMENT = 100;

    // ********************************* 我的申请 请求 key  和 value

    public static final String KEY_APPLICATION = "module";//key

    public static final String KEY_APPLICATION_LEAVE = "leave";//请假

    public static final String KEY_APPLICATION_EVECTION = "evection";//出差

    public static final String KEY_APPLICATION_OVERTIME = "overtime";//加班

    public static final String KEY_APPLICATION_TRAVEL = "travel";//差旅费报销

    public static final String KEY_APPLICATION_REIMBURSEMENT = "reimbursement";//报销

    public static final String KEY_APPLICATION_RESERVE = "standby";//备用金申请

    public static final String KEY_APPLICATION_GO_OUT = "goout";//外出申请

    public static final String KEY_APPLICATION_CONTRACT = "contract_apply";//合同申请

    public static final String KEY_APPLICATION_PAY_APPLY= "pay_apply";//付款申请

    public static final String KEY_APPLICATION_POSITIVE= "positive";//转正申请

    public static final String KEY_APPLICATION_PROCURMENT= "procurement";//’采购申请

    public static final String KEY_APPLICATION_RECEIVE= "receive";//物品领用申请

    public static final String KEY_APPLICATION_RECRUITMENT= "recruitment";//招聘申请

    public static final String KEY_APPLICATION_USECAR= "usecar";//用车申请

    public static final String KEY_APPLICATION_USESEAL= "useseal";//用章申请


    //默认文字

    public static final String TOAST_NO_MORE_DATA = "暂无更多数据";

    public static final String TOAST_LODING = "加载中...";

}

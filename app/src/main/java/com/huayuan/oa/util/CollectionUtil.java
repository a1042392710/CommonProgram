package com.huayuan.oa.util;

import java.util.List;

/**
 * @author chenhao 2018/10/12
 * @function 集合工具类
 */
public class CollectionUtil {


    /**
     * 判断集合是否为空
     * @param list 集合
     * @param <T> 类型
     * @return 是/否
     */
    public  static <T>boolean isEmpty(List<T> list){
        if (null!=list&&list.size()>0){
            return false;
        }
        return true;
    }
}

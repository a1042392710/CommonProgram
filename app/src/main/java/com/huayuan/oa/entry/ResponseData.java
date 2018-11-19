package com.huayuan.oa.entry;

import com.huayuan.oa.util.networkutil.entry.StatusBean;

import java.io.Serializable;

/**
 * @param <T>
 * @create CHH
 */
public class ResponseData<T> implements Serializable {


    private static final long serialVersionUID = 1L;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //返回的数据
    private T data;
    //返回状态
    private StatusBean status;


    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

}

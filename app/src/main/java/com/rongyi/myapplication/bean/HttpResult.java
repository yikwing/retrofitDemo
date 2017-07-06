package com.rongyi.myapplication.bean;

/**
 * Created by rongyi on 2017/7/5.
 */

public class HttpResult<T> {


    private String message;
    private int code;
    private int pageRow;
    private T list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPageRow() {
        return pageRow;
    }

    public void setPageRow(int pageRow) {
        this.pageRow = pageRow;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }


}

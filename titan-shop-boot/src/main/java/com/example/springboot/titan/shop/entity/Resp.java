package com.example.springboot.titan.shop.entity;

import lombok.Data;

/**
 * @author Eric.Mo
 * @since 2019/1/6
 */
@Data
public class Resp {

    private String code;
    private String msg;
    private Object data;

    public Resp() {

    }

    public Resp(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public Resp(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Resp(Object data) {
        this.code = "200";
        this.msg = "success";
        this.data = data;
    }
}

package com.dx.proxypool.bean;

import lombok.Data;

/**
 * Created by daixiang on 2018/8/1.
 */
@Data
public class ProxyBean {

    private String ip;

    // 评分
    private String score;

    private Integer port;

    // 位置
    private String location;

    // 运营商
    private String operator;

    // 类型
    private String type;

    // 匿名
    private String anonymousType;
    // 支持https
    private boolean Https;
    // 支持post请求
    private boolean Post;
    // 延迟m
    private String delay;

}

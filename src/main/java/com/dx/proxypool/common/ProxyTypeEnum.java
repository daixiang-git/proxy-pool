package com.dx.proxypool.common;

/**
 * Created by daixiang on 2018/8/16.
 */

public enum ProxyTypeEnum {
    QUICK("QUICKProxy"),
    MN("MNProxy");

    ProxyTypeEnum(String name) {
        this.name = name;
    }

    private String name;
}

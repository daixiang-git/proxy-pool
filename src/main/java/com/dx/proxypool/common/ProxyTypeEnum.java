package com.dx.proxypool.common;

/**
 * Created by daixiang on 2018/8/16.
 */

public enum ProxyTypeEnum {
    QUICK("QUICKProxy", "https://proxy.coderbusy.com/classical/country/cn.aspx"), MN("MNProxy", "");

    ProxyTypeEnum(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    private String name;
    private String url;

}

package com.dx.proxypool.service;

import com.dx.proxypool.bean.ProxyBean;

import java.util.List;

/**
 * Created by daixiang on 2018/8/1.
 */

public interface ProxyService {

    ProxyBean getProxy();

    boolean deleteProxy(ProxyBean proxyBean);

    int addProxy(ProxyBean proxyBean);

    List<ProxyBean> getList();
}

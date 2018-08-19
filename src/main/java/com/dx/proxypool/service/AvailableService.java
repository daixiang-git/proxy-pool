package com.dx.proxypool.service;

import com.dx.proxypool.bean.ProxyBean;

import java.util.List;

/**
 * Created by daixiang on 2018/8/2.
 */

public interface AvailableService {
    void isAvailable(ProxyBean proxyBean);

    void isAvailableOfList(List<ProxyBean> proxyBeanList);
}

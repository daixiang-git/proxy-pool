package com.dx.proxypool.service;

import com.dx.proxypool.bean.ProxyBean;

/**
 * Created by daixiang on 2018/8/2.
 */

public interface AvailableService {
    boolean isAvailable(ProxyBean proxyBean);
}

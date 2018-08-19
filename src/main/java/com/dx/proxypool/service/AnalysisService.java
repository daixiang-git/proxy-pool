package com.dx.proxypool.service;

import java.io.IOException;
import java.util.List;

import com.dx.proxypool.bean.ProxyBean;
import com.dx.proxypool.common.ProxyTypeEnum;

/**
 * Created by daixiang on 2018/8/2.
 */

public interface AnalysisService {
    /**
     * 爬取解析html
     *
     * @return
     */
    List<ProxyBean> spiderAndAnalysis() throws IOException;

    ProxyTypeEnum supportType();
}

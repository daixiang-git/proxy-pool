package com.dx.proxypool.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.dx.proxypool.bean.ProxyBean;

/**
 * Created by daixiang on 2018/8/2.
 */

public interface BaseService {
    /**
     * 解析html
     *
     * @param document
     * @param lastProxyBean
     * @return
     */
    List<ProxyBean> analysisHtml(Document document, ProxyBean lastProxyBean);
}

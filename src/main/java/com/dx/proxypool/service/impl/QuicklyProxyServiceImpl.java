package com.dx.proxypool.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.dx.proxypool.common.ProxyTypeEnum;
import com.dx.proxypool.service.AnalysisService;
import com.dx.proxypool.util.SpiderUtil;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dx.proxypool.bean.ProxyBean;
import com.google.common.collect.Lists;

/**
 * Created by daixiang on 2018/8/4.
 */

public class QuicklyProxyServiceImpl implements AnalysisService {
    @Override
    public List<ProxyBean> analysisHtml(Document document, ProxyBean lastProxyBean) {
        List<ProxyBean> proxyBeanList = Lists.newArrayList();
        Elements elements = document.getElementsByTag("tbody");
        if (CollectionUtils.isEmpty(elements) || elements.size() == 1) {
            return Collections.emptyList();
        }
        int key = 0;
        for (Element element : elements) {
            if (element.childNodeSize() != 25 || key == 0) {
                key = 1;
                continue;
            }
            ProxyBean proxyBean = new ProxyBean();
            proxyBean.setIp(element.child(0).text().trim());
            proxyBean.setScore(element.child(1).text().trim());
            proxyBean.setPort(Integer.valueOf(element.child(1).text().trim()));
            if (lastProxyBean != null && Objects.equals(lastProxyBean.getIp(), proxyBean.getIp())) {
                return proxyBeanList;
            }
            proxyBean.setLocation(element.child(3).text().trim());
            proxyBean.setOperator(element.child(4).text().trim());
            proxyBean.setType(element.child(5).text().trim());
            proxyBean.setAnonymousType(element.child(7).text().trim());
            proxyBean.setHttps(element.child(8).childNodeSize() > 1 ? true : false);
            proxyBean.setPost(element.child(9).childNodeSize() > 1 ? true : false);
            proxyBean.setDelay(element.child(10).text().trim());
            proxyBeanList.add(proxyBean);
        }
        return proxyBeanList;
    }

    @Override
    public ProxyTypeEnum supportType() {
        return ProxyTypeEnum.QUICK;
    }

    public static void main(String[] args) {
        QuicklyProxyServiceImpl quicklyProxyServiceImpl = new QuicklyProxyServiceImpl();
        Document document = SpiderUtil.getByHttpRequest("https://www.kuaidaili.com/free/inha/");
        quicklyProxyServiceImpl.analysisHtml(document, null);
    }
}

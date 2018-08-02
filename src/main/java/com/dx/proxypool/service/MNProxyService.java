package com.dx.proxypool.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.dx.proxypool.bean.ProxyBean;
import com.google.common.collect.Lists;

/**
 * Created by daixiang on 2018/8/2.
 */
@Service
public class MNProxyService implements BaseService {

    @Override
    public List<ProxyBean> analysisHtml(Document document, ProxyBean lastProxyBean) {
        List<ProxyBean> proxyBeanList = Lists.newArrayList();
        Elements elements = document.getElementsByTag("tr");
        if (elements == null) {
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
            proxyBean.setPort(Integer.valueOf(element.child(2).text().trim()));
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
}

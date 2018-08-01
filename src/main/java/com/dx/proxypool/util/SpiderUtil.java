package com.dx.proxypool.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dx.proxypool.bean.ProxyBean;
import com.google.common.collect.Lists;

/**
 * Created by daixiang on 2018/8/1.
 */

public class SpiderUtil {

    // ip验证url
    private static final String IPVALIDATEURL = "http://www.ip138.com/";

    // ip验证url 站长之家
    private static final String IPVALIDATEURL1 = "http://ip.chinaz.com/";

    /**
     * 解析html
     * 
     * @param document
     * @param lastProxyBean
     * @return
     */
    public static List<ProxyBean> analysisHtml(Document document, ProxyBean lastProxyBean) {
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

    /**
     * 判断代理是否可用
     * 
     * @param proxyBean
     * @return
     */
    public static boolean isAvailable(ProxyBean proxyBean) {
        // String html = HttpRequest.sendGet(IPVALIDATEURL, proxyBean);
        String html = HttpRequest.sendGet(IPVALIDATEURL1, proxyBean);
        if (StringUtils.isEmpty(html)) {
            return false;
        }
        Document document = Jsoup.parse(html);
        if (document == null) {
            return false;
        }
        // Elements elements = document.getElementsByTag("center");
        // if (CollectionUtils.isEmpty(elements)) {
        // return false;
        // }

        Elements elements = document.getElementsByClass("getlist pl10");
        if (CollectionUtils.isEmpty(elements)) {
            return false;
        }
        String text = elements.get(0).text().trim();
        System.out.println(proxyBean.getIp() + ":" + proxyBean.getLocation());
        System.out.println(text);
        System.out.println("---------------------------------");
        return text.contains(proxyBean.getIp()) ? true : false;

    }
}

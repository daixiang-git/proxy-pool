package com.dx.proxypool.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.dx.proxypool.bean.ProxyBean;

/**
 * Created by daixiang on 2018/8/1.
 */

public class SpiderUtil {

    // ip验证url
    private static final String IPVALIDATEURL = "http://www.ip138.com/";

    // ip验证url 站长之家
    private static final String IPVALIDATEURL1 = "http://ip.chinaz.com/";

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

package com.dx.proxypool.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dx.proxypool.bean.ProxyBean;

/**
 * Created by daixiang on 2018/8/1.
 */

public class SpiderUtil {

    private static final Logger logger = LoggerFactory.getLogger(SpiderUtil.class);

    private static final String PHANTOMJS = "/Users/daixiang/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs /Users/daixiang/IdeaProjects/proxy-pool/src/main/resources/code.js ";

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
        if (text.contains(proxyBean.getIp())) {
            logger.info(text);
            proxyBean.setValidateTime(new Date());
            return true;
        }
        return false;

    }

    public static Document getByPhantomjs(String url) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String exec = PHANTOMJS + url;
        Process p = rt.exec(exec);
        InputStream is = p.getInputStream();
        return Jsoup.parse(is, "utf-8", url);
    }

    public static Document getByHttpRequest(String url) {
        String html = HttpRequest.sendGet(url, null);
        return Jsoup.parse(html);
    }

    public static Document getByHttpRequestOfProxy(String url, ProxyBean proxyBean) {
        String html = HttpRequest.sendGet(url, proxyBean);
        return Jsoup.parse(html);
    }
}

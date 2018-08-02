package com.dx.proxypool.timetask;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.dx.proxypool.service.AvailableService;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dx.proxypool.bean.ProxyBean;
import com.dx.proxypool.service.MNProxyService;
import com.dx.proxypool.util.RedisUtil;
import com.dx.proxypool.util.SpiderUtil;

import net.sf.json.JSONObject;

/**
 * Created by daixiang on 2018/3/8.
 */
@Component
public class TimeTask {

    private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);

    // 中国代理url
    private static final String IPPROXYURL = "https://proxy.coderbusy.com/classical/country/cn.aspx";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MNProxyService mnProxyService;

    @Autowired
    private AvailableService availableService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void syncTask() throws IOException {
        logger.info("每天1分钟同步一次预约任务");

        Runtime rt = Runtime.getRuntime();
        String exec = "/Users/daixiang/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs /Users/daixiang/IdeaProjects/proxy-pool/src/main/resources/code.js "
                + IPPROXYURL;
        Process p = rt.exec(exec);
        InputStream is = p.getInputStream();

        Document document = Jsoup.parse(is, "utf-8", IPPROXYURL);
        if (document == null) {
            return;
        }

        String lastProxy = (String) redisUtil.get("last");
        ProxyBean lastProxyBean = (ProxyBean) JSONObject.toBean(JSONObject.fromObject(lastProxy), ProxyBean.class);

        List<ProxyBean> proxies = mnProxyService.analysisHtml(document, lastProxyBean);
        if (!CollectionUtils.isEmpty(proxies)) {
            redisUtil.add("last", JSONObject.fromObject(proxies.get(proxies.size() - 1)).toString());
        }

        proxies.stream().forEach(b -> availableService.isAvailable(b));

    }

    public static void main(String[] args) throws IOException {
        // syncTask();
    }
}

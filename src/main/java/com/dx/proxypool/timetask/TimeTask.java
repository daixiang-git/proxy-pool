package com.dx.proxypool.timetask;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dx.proxypool.bean.ProxyBean;
import com.dx.proxypool.common.ProxyTypeEnum;
import com.dx.proxypool.service.AvailableService;
import com.dx.proxypool.service.AnalysisService;
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
    private static final String MNURL = "https://proxy.coderbusy.com/classical/country/cn.aspx";

    // 快代理高匿url
    private static final String QUICKURL = "https://www.kuaidaili.com/free/inha/";

    // 快代理普通url
    private static final String QUICKCOMMONURL = "https://www.kuaidaili.com/free/intr/";

    @Autowired
    private List<AnalysisService> analysisServices;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void mnProxy() throws IOException {
        logger.info("每10分钟执行抓取任务");

        List<ProxyBean> proxies = chooseService(ProxyTypeEnum.MN).spiderAndAnalysis();

    }

    private AnalysisService chooseService(ProxyTypeEnum type) {
        for (AnalysisService analysisService : analysisServices) {
            if (analysisService.supportType().equals(type)) {
                return analysisService;
            }
        }
        throw new RuntimeException("出问题了辣鸡！！！");
    }
}

package com.dx.proxypool.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dx.proxypool.bean.ProxyBean;
import com.dx.proxypool.common.ProxyTypeEnum;
import com.dx.proxypool.service.AnalysisService;
import com.dx.proxypool.service.AvailableService;
import com.dx.proxypool.service.ServiceTemplate;
import com.dx.proxypool.util.RedisUtil;
import com.dx.proxypool.util.SpiderUtil;
import com.google.common.collect.Lists;

import net.sf.json.JSONObject;

/**
 * Created by daixiang on 2018/8/2.
 */
@Service
public class MNProxyServiceImpl implements AnalysisService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AvailableService availableService;

    @Override
    public List<ProxyBean> spiderAndAnalysis() throws IOException {
        return new ServiceTemplate<List<ProxyBean>>() {
            Document document = SpiderUtil.getByPhantomjs(ProxyTypeEnum.MN.getUrl());
            ProxyBean lastProxyBean;
            List<ProxyBean> proxyBeanList = Lists.newArrayList();

            @Override
            protected void beforeProcess() {
                String lastProxy = (String) redisUtil.get(ProxyTypeEnum.MN.getName());
                lastProxyBean = (ProxyBean) JSONObject.toBean(JSONObject.fromObject(lastProxy), ProxyBean.class);
            }

            @Override
            protected List<ProxyBean> process() {
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

            @Override
            protected void onSuccess() {
                if (!CollectionUtils.isEmpty(proxyBeanList)) {
                    redisUtil.add(ProxyTypeEnum.MN.getName(), JSONObject.fromObject(proxyBeanList.get(0)).toString());
                }
                availableService.isAvailableOfList(proxyBeanList);
            }
        }.execute();
    }

    @Override
    public ProxyTypeEnum supportType() {
        return ProxyTypeEnum.MN;
    }
}

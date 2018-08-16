package com.dx.proxypool.service.impl;

import com.dx.proxypool.bean.ProxyBean;
import com.dx.proxypool.service.AvailableService;
import com.dx.proxypool.util.RedisUtil;
import com.dx.proxypool.util.SpiderUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by daixiang on 2018/8/2.
 */
@Service
public class AvailableServiceImpl implements AvailableService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Async("asyncServiceExecutor")
    public void isAvailable(ProxyBean proxyBean) {
        if (SpiderUtil.isAvailable(proxyBean)) {
            redisUtil.add(proxyBean.getIp(), JSONObject.fromObject(proxyBean).toString());
        }
    }
}

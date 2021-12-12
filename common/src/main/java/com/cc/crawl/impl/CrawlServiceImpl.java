package com.cc.crawl.impl;

import com.cc.crawl.CrawlService;
import com.cc.dao.impl.StartUrlDaoImpl;
import com.cc.doamin.StartUrl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CrawlServiceImpl implements CrawlService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CrawlServiceImpl.class);

    public static void main(String[] args) {
        // new CrawlServiceImpl().crawl("AA", 3);
    }

    @Override
    public boolean crawl(StartUrl startUrl, int m) {
        boolean ret = true;

        startUrl.setStatusCode("3");
        StartUrlDaoImpl startUrlDao = new StartUrlDaoImpl();
        boolean update = startUrlDao.updateStartUrl(startUrl);
        if (!update) {
            return update;
        }

        for (int i = 1; i <= m; i++) {
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("param1", "p1-" + startUrl + "-" + i);
            urlParams.put("param2", "p2-" + startUrl + "-" + i);


            Map<String, String> headers = new HashMap<>();
            headers.put("header1", "h1-" + startUrl + "-" + i);
            headers.put("header2", "h2-" + startUrl + "-" + i);
            headers.put("Content-Type", "application/json");


            String body = "{" +
                    "\"startUrl\" : \"name1" + startUrl + "-" + i + "\"," +
                    "\"password\" : \"password1" + startUrl + "-" + i + "\"" +
                    "}";


            String s = startUrl.getUrl(); //HttpClientHelper.httpClientPost("http://127.0.0.1:8080/hello", headers, urlParams, body, "UTF-8");
            LOGGER.info("crawled startUrl = " + startUrl);
            ret = ret && StringUtils.isNotBlank(s);
        }

        if (ret) {
            startUrl.setStatusCode("0");
        } else {
            startUrl.setStatusCode("1");
        }

        boolean b = startUrlDao.updateStartUrl(startUrl);
        return ret && b;
    }
}

package com.cc.crawl;

import com.cc.doamin.StartUrl;

public interface CrawlService {

    boolean crawl(StartUrl startUrl, int m);
}

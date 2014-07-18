package com.demofoio.crawler.job;

import com.demofoio.crawler.page.Page;

import java.net.URI;

/**
 * @author : lihaoquan
 */
public class TestJob implements Job {

    /**
     * 是否访问目标地址
     * @param url
     * @return
     */
    @Override
    public boolean visit(URI url) {
        return url.toString().startsWith("http://en.wikinews.org/wiki/");
    }

    @Override
    public void process(Page page) {
        // do something what you like
    }
}

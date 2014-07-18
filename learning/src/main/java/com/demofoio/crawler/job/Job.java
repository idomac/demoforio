package com.demofoio.crawler.job;

import com.demofoio.crawler.page.Page;

import java.net.URI;

/**
 * @author : lihaoquan
 */
public interface Job {

    boolean visit(URI uri);

    void process(Page page);
}

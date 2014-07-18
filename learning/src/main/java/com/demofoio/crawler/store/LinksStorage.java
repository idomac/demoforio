package com.demofoio.crawler.store;

import java.net.URI;

/**
 * @author : lihaoquan
 */
public interface LinksStorage {

    boolean add(URI url);
}

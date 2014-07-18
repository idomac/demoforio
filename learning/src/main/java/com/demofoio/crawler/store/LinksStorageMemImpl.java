package com.demofoio.crawler.store;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : lihaoquan
 *
 * 用于存放处理过的URI对象
 */
public class LinksStorageMemImpl implements LinksStorage {

    private Set<URI> links = new HashSet<URI>();

    @Override
    public boolean add(URI url) {
        return links.add(url);
    }
}

package com.demofoio.crawler.page;

import java.net.URI;
import java.util.Map;

/**
 * @author : lihaoquan
 * 网页对象
 */
public class Page {

    private URI                 uri;

    private byte[]              data;//页面数据

    private int                 statusCode;//状态码

    private Map<String, String> headers;//报文头

    private String              body;//报文体

    public Page(URI uri, byte[] data)
    {
        this.uri = uri;
        this.data = data;
    }
}

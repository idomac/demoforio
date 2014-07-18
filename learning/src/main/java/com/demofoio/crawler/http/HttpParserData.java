package com.demofoio.crawler.http;

import java.util.Collections;
import java.util.Map;

/**
 * @author : lihaoquan
 *
 * Http解析数据对象
 */
public class HttpParserData {

    private int                 statusCode;//状态码

    private String              httpVersion;//版本信息

    private String              reasonPhrase;

    private Map<String, String> httpFields;

    private String              body;

    public HttpParserData(int statusCode, String httpVersion, String reasonPhrase, Map<String, String> httpFields, String body)
    {
        this.httpFields = Collections.unmodifiableMap(httpFields);
        this.reasonPhrase = reasonPhrase;
        this.statusCode = statusCode;
        this.httpVersion = httpVersion;
        this.body = body;
    }

    public Map<String, String> getHttpFields()
    {
        return httpFields;
    }

    public String getReasonPhrase()
    {
        return reasonPhrase;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public String getHttpVersion()
    {
        return httpVersion;
    }

    public String getBody()
    {
        return body;
    }

}

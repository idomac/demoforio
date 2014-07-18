package com.demofoio.crawler.page;

import com.demofoio.crawler.http.HttpParser;
import com.demofoio.crawler.http.HttpParserData;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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

    public URI getUri()
    {
        return uri;
    }

    public String getBody()
    {
        return body;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public String getHeader(String name)
    {
        return headers.get(name);
    }


    /**
     * 处理函数
     */
    public void process() {
        HttpParser parser = new HttpParser();
        HttpParserData parserData = parser.parse(data);

        this.body = parserData.getBody();
        this.headers = parserData.getHttpFields();
        this.statusCode = parserData.getStatusCode();
    }


    /**
     * 获取连接信息
     * @return
     */
    public List<URI> getLinks()
    {
        Document doc = Jsoup.parse(body);
        Elements elems = doc.select("a[href]");

        ArrayList<URI> links = new ArrayList<URI>();
        for (Element elem : elems)
        {
            try
            {
                String href = elem.attr("href");

                // see
                // http://stackoverflow.com/questions/724043/http-url-address-encoding-in-java
                URI link = new URI(href.replaceAll("\\s", "%20"));
                link = new URI(link.toASCIIString());

                link = uri.resolve(link);

                if (StringUtils.isNotBlank(link.getHost()))
                {
                    links.add(link);
                }
                // else: this is probably not a http link, skip it
            }
            catch (URISyntaxException e)
            {
                e.printStackTrace();
            }
        }
        return links;
    }


}

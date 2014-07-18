package com.demofoio.crawler.page;

import com.demofoio.crawler.job.Job;
import com.demofoio.crawler.store.LinksStorage;

import java.net.URI;
import java.util.concurrent.BlockingQueue;

/**
 * @author : lihaoquan
 */
public class PageHandler implements Runnable {

    private BlockingQueue<Page>    pagesQueue;

    private BlockingQueue<URI>     linksQueue;

    private Job                    job;

    private LinksStorage           linksStorage;


    public PageHandler(BlockingQueue<Page> pagesQueue,BlockingQueue<URI> linksQueue,Job job,LinksStorage linksStorage) {
        this.pagesQueue = pagesQueue;
        this.linksQueue = linksQueue;
        this.job = job;
        this.linksStorage = linksStorage;
    }


    /**
     * 把uri添加到队列里面
     * 添加的前提:
     * url是允许访问的,而且不存在于已经访问的队列(linksStorage)中
     *
     * @param url
     */
    private void add(URI url)
    {
        if (job.visit(url) && linksStorage.add(url))
        {
            linksQueue.add(url);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Page page = pagesQueue.take();//阻塞获取
                System.out.println("正在处理 : "+page.getUri());

                page.process();

                if (page.getStatusCode() == 200) {//OK 正常

                    System.out.println("200");

                }else if (page.getStatusCode() == 301 || page.getStatusCode() == 302) {

                }else {

                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

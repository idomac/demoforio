package com.demofoio.crawler;

import com.demofoio.crawler.http.HttpFetcher;
import com.demofoio.crawler.job.TestJob;
import com.demofoio.crawler.page.Page;
import com.demofoio.crawler.page.PageHandler;
import com.demofoio.crawler.store.LinksStorageMemImpl;

import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author : lihaoquan
 *
 */
public class Crawler {

    public static void main(String[] args) throws Exception {

        TestJob crawler = new TestJob();

        /**
         * BlockingQueue是一个由数组支持的有界阻塞队列，也就是说当一个线程向一个固定大小的BlockingQueue队列里面不停地存放数据，
         * 另一个线程不停的向这个队列里面取数据，如果队列满了，还继续存放数据，此时出现阻塞，直到队列有空闲的位置；
         * 反之，如果队列为空，还继续取数据，则会出现阻塞，直到队列中有数据为止
         */
        BlockingQueue<URI> linksQueue = new LinkedBlockingQueue<URI>();

        //存放初始化的对象
        linksQueue.put(new URI("http://en.wikinews.org/wiki/Main_Page"));

        /**
         * Page对象队列
         */
        BlockingQueue<Page> pagesQueue = new LinkedBlockingQueue<Page>();

        LinksStorageMemImpl linksStorage = new LinksStorageMemImpl();

        PageHandler pageHandler = new PageHandler(pagesQueue,linksQueue,crawler,linksStorage);

        /**
         * 守护线程
         */
        Thread thread = new Thread(pageHandler);
        thread.setDaemon(true);
        thread.start();

        /**
         * http抓取线程
         */
        HttpFetcher client = new HttpFetcher(linksQueue,pagesQueue);
        client.fetch();
    }
}
